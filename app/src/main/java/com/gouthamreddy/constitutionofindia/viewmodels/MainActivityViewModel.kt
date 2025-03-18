package com.gouthamreddy.constitutionofindia.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gouthamreddy.constitutionofindia.data.models.AmendmentEntity
import com.gouthamreddy.constitutionofindia.data.models.ArticleEntity
import com.gouthamreddy.constitutionofindia.data.models.ConstitutionCombinedResponseItem
import com.gouthamreddy.constitutionofindia.data.models.PreambleEntity
import com.gouthamreddy.constitutionofindia.data.models.PreambleResponse
import com.gouthamreddy.constitutionofindia.data.models.ScheduleEntity
import com.gouthamreddy.constitutionofindia.data.models.SchedulesResponse
import com.gouthamreddy.constitutionofindia.data.models.SearchResult
import com.gouthamreddy.constitutionofindia.domain.ConstitutionRepository
import com.gouthamreddy.constitutionofindia.domain.usecase.FetchCombinedJSONDataUseCase
import com.gouthamreddy.constitutionofindia.domain.usecase.FetchPreambleJSONDataUseCase
import com.gouthamreddy.constitutionofindia.domain.usecase.FetchSchedulesJSONDataUseCase
import com.gouthamreddy.constitutionofindia.mappers.ArticleJsonToEntityMapper
import com.gouthamreddy.constitutionofindia.mappers.ScheduleJsonToEntityMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    val fetchSchedulesJSONDataUseCase: FetchSchedulesJSONDataUseCase,
    val fetchPreambleJSONDataUseCase: FetchPreambleJSONDataUseCase,
    val dbRepository: ConstitutionRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<MainActivityState>(MainActivityState())
    val state = _state.asStateFlow()

    init {
        startPolling()
    }

    fun fetchDataFromRemote() {
        viewModelScope.launch {
            val articles = dbRepository.getAllArticles().firstOrNull()
            if (articles.isNullOrEmpty()) {
                fetchCombinedJSONDataUseCase(Unit).onSuccess { response ->
                   updateArticlesInDatabase(response)
                }.onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = it.errorMessage
                        )
                    }
                    Log.e("MainActivityViewModel", "Error: ${it.message}")
                }
            }
            val schedules = dbRepository.getAllSchedules().firstOrNull()
            if (schedules.isNullOrEmpty()) {
                fetchSchedulesJSONDataUseCase(Unit).onSuccess { response ->
                    updateSchedulesInDatabase(response)
                }.onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = it.errorMessage
                        )
                    }
                    Log.e("MainActivityViewModel", "Error: ${it.message}")
                }
            }
            val preamble = dbRepository.getPreamble().firstOrNull()
            if (preamble.isNullOrEmpty()) {
                fetchPreambleJSONDataUseCase(Unit).onSuccess { response ->
                    updatePreambleInDatabase(response)
                }.onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = it.errorMessage
                        )
                    }
                    Log.e("MainActivityViewModel", "Error: ${it.message}")
                }
            }
        }
    }

    private fun updateArticlesInDatabase(list: List<ConstitutionCombinedResponseItem>) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.insertArticles(list.map { ArticleJsonToEntityMapper().map(it) })

        }
    }

    private fun updateSchedulesInDatabase(list: List<SchedulesResponse>) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.insertSchedules(list.map { ScheduleJsonToEntityMapper().map(it) })
        }
    }

    private fun updatePreambleInDatabase(preamble: PreambleResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.insertPreamble(
                PreambleEntity(
                    title = "The Preamble",
                    content = preamble.content
                )
            )

        }
    }

    private fun startPolling() {
        viewModelScope.launch {
            var i =0
            while (true) {
                syncLocalStateWithDB()
                delay(60_000) // Wait for 60 seconds
                i++
                if (isDataAvailable() || i > 10) break
            }
        }
    }

    private fun isDataAvailable(): Boolean {
        return state.value.preamble.content.isNotEmpty() &&
                state.value.schedulesList.isNotEmpty() &&
                state.value.articlesList.isNotEmpty()
                //&& state.value.amendmentsList.isNotEmpty()
    }

    private fun syncLocalStateWithDB() {
        viewModelScope.launch {
            dbRepository.getPreamble().collectLatest { preamble ->
                _state.update {
                    it.copy(
                        preamble = preamble.firstOrNull() ?: PreambleEntity(
                            title = "Loading",
                            content = ""
                        ),
                    )
                }
            }
        }
        viewModelScope.launch {
            dbRepository.getAllSchedules().collectLatest { schedules ->
                _state.update {
                    it.copy(
                        schedulesList = schedules,
                    )
                }
            }
        }
        viewModelScope.launch {
            dbRepository.getAllArticles().collectLatest { articles ->
                _state.update {
                    it.copy(
                        articlesList = articles,
                    )
                }
            }
        }
        viewModelScope.launch {

            dbRepository.getAllAmendments().collectLatest { amendments ->
                _state.update {
                    it.copy(
                        amendmentsList = amendments,
                    )
                }
            }
        }


    }

    fun search(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(searchResults = dbRepository.searchDatabase(query)) }
        }
    }

}

data class MainActivityState(
    val articlesList: List<ArticleEntity> = emptyList<ArticleEntity>(),
    val schedulesList: List<ScheduleEntity> = emptyList<ScheduleEntity>(),
    val amendmentsList: List<AmendmentEntity> = emptyList<AmendmentEntity>(),
    val searchResults: List<SearchResult> = emptyList<SearchResult>(),
    val preamble: PreambleEntity = PreambleEntity(title = "Loading", content = ""),
    val isLoading: Boolean = true,
    val errorMessage: String? = null

)