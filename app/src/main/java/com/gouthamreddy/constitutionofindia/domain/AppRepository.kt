package com.gouthamreddy.constitutionofindia.domain


import com.gouthamreddy.constitutionofindia.data.ApiService
import com.gouthamreddy.constitutionofindia.data.models.ConstitutionCombinedResponseItem
import com.gouthamreddy.constitutionofindia.data.models.PreambleResponse
import com.gouthamreddy.constitutionofindia.data.models.SchedulesResponse

class AppRepository(private val apiService: ApiService) {
    suspend fun getCombinedResponse(): Result<List<ConstitutionCombinedResponseItem>> {
        return runCatching { apiService.getCombinedResponse() }
    }

    suspend fun getPreambleResponse(): Result<PreambleResponse> {
        return runCatching { apiService.getPreambleResponse() }
    }

    suspend fun getSchedulesResponse(): Result<List<SchedulesResponse>> {
        return runCatching { apiService.getSchedules() }
    }
}