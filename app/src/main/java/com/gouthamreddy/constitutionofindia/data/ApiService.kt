package com.gouthamreddy.constitutionofindia.data


import com.gouthamreddy.constitutionofindia.data.models.ConstitutionCombinedResponseItem
import com.gouthamreddy.constitutionofindia.data.models.PreambleResponse
import com.gouthamreddy.constitutionofindia.data.models.SchedulesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {


    @GET("api/combined")
    suspend fun getCombinedResponse(): Response<List<ConstitutionCombinedResponseItem>>

    @GET("api/schedules")
    suspend fun getSchedules(): Response<List<SchedulesResponse>>


    @GET("api/preamble")
    suspend fun getPreambleResponse(): Response<PreambleResponse>


}
