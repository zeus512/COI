package com.gouthamreddy.constitutionofindia.domain


import com.gouthamreddy.constitutionofindia.data.ApiService
import okhttp3.ResponseBody
import retrofit2.Response

class AppRepository(private val apiService: ApiService) {

    suspend fun pdfDownloadRequest(fileUrl: String): Response<ResponseBody> {
        return apiService.downloadPdf(fileUrl)
    }


}