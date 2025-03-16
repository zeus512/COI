package com.gouthamreddy.constitutionofindia.domain


import com.gouthamreddy.constitutionofindia.data.ApiService
import com.gouthamreddy.constitutionofindia.data.models.ConstitutionCombinedResponseItem
import com.gouthamreddy.constitutionofindia.data.models.PreambleResponse
import com.gouthamreddy.constitutionofindia.data.models.SchedulesResponse
import retrofit2.Response

class AppRepository(private val apiService: ApiService) {


    suspend fun getCombinedResponse(): Response<List<ConstitutionCombinedResponseItem>> {
        return apiService.getCombinedResponse()
    }
    suspend fun getPreambleResponse(): Response<PreambleResponse> {
        return apiService.getPreambleResponse()
    }
    suspend fun getSchedulesResponse(): Response<List<SchedulesResponse>> {
        return apiService.getSchedules()
    }


}