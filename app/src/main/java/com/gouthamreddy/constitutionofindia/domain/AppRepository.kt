package com.gouthamreddy.constitutionofindia.domain


import com.gouthamreddy.constitutionofindia.data.ApiService
import com.gouthamreddy.constitutionofindia.data.models.ConstitutionCombinedResponseItem
import retrofit2.Response

class AppRepository(private val apiService: ApiService) {


    suspend fun getCombinedResponse(): Response<List<ConstitutionCombinedResponseItem>> {
        return apiService.getCombinedResponse()
    }


}