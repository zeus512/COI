package com.gouthamreddy.constitutionofindia.domain.usecase

import com.gouthamreddy.constitutionofindia.domain.AppRepository
import com.gouthamreddy.constitutionofindia.domain.UseCase
import okhttp3.ResponseBody

class PDFDownloaderUseCase(private val repository: AppRepository) :
    UseCase<Unit, ResponseBody> {

    override suspend fun invoke(params: Unit): Result<ResponseBody> {
        //return  repository.pdfDownloadRequest(Constants.DOWNLOAD_URL).toResult()
        return Result.failure(Exception("Not Implemented"))
    }
}
