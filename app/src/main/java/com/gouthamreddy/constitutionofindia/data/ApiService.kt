package com.gouthamreddy.constitutionofindia.data


import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApiService {
    @GET("/users")
    suspend fun getUsers(): Response<List<Dummy>>

    @Streaming
    @GET
    suspend fun downloadPdf(@Url fileUrl: String): Response<ResponseBody>

}

data class Dummy(val dummy: String)