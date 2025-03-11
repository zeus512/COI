package com.gouthamreddy.constitutionofindia.data


import com.gouthamreddy.constitutionofindia.data.models.ConstitutionCombinedResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {


    @GET("api/combined")
    suspend fun getCombinedResponse(): Response<List<ConstitutionCombinedResponseItem>>

//    @Streaming
//    @GET
//    suspend fun downloadPdf(@Url fileUrl: String): Response<ResponseBody>

}
