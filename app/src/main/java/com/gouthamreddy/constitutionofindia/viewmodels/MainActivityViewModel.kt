package com.gouthamreddy.constitutionofindia.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gouthamreddy.constitutionofindia.data.models.AmendmentEntity
import com.gouthamreddy.constitutionofindia.data.models.ArticleEntity
import com.gouthamreddy.constitutionofindia.data.models.ConstitutionCombinedResponseItem
import com.gouthamreddy.constitutionofindia.data.models.ScheduleEntity
import com.gouthamreddy.constitutionofindia.domain.ConstitutionRepository
import com.gouthamreddy.constitutionofindia.domain.usecase.FetchCombinedJSONDataUseCase
import com.gouthamreddy.constitutionofindia.mappers.ArticleJsonToEntityMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val fetchCombinedJSONDataUseCase: FetchCombinedJSONDataUseCase,
    val dbRepository: ConstitutionRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<MainActivityState>(MainActivityState())
    val state = _state.asStateFlow()

    init {
        syncLocalStateWithDB()
    }

    fun fetchCombinedJSONData() {
        viewModelScope.launch {
            val articles = dbRepository.getAllArticles().firstOrNull()
            if (articles.isNullOrEmpty()) {
                fetchCombinedJSONDataUseCase(Unit).onSuccess { response ->
                    Log.d("MainActivityViewModel", "Success: $response")
                    updateDatabase(response)
                }.onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = it.errorMessage
                        )
                    }
                    Log.e("MainActivityViewModel", "Error: ${it.message}")
                }
            } else {
                Log.d("MainActivityViewModel", "DB has articles don't fetch")
            }
        }
    }

    private fun updateDatabase(list: List<ConstitutionCombinedResponseItem>) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.insertArticles(list.map { ArticleJsonToEntityMapper().map(it) })

        }
    }

    private fun syncLocalStateWithDB() {
        viewModelScope.launch {
            dbRepository.getAllArticles().collectLatest { articles ->
                _state.update {
                    it.copy(
                        articlesList = articles,
                    )
                }
            }
            dbRepository.getAllSchedules().collectLatest { schedules ->
                _state.update {
                    it.copy(
                        schedulesList = schedules,
                    )
                }
            }
            dbRepository.getAllAmendments().collectLatest { amendments ->
                _state.update {
                    it.copy(
                        amendmentsList = amendments,
                    )
                }
            }

        }
    }

}

data class MainActivityState(
    val articlesList: List<ArticleEntity> = emptyList<ArticleEntity>(),
    val schedulesList: List<ScheduleEntity> = emptyList<ScheduleEntity>(),
    val amendmentsList: List<AmendmentEntity> = emptyList<AmendmentEntity>(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null

)