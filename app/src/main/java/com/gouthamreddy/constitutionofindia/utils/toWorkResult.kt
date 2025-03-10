package com.gouthamreddy.constitutionofindia.utils

import android.util.Log
import androidx.work.Data
import androidx.work.ListenableWorker
import okhttp3.ResponseBody

fun Result<ResponseBody>.toWorkResult(
    onSuccess: (ResponseBody) -> Boolean,
    onFailureMessage: String,
    outputDataKey: String,
): ListenableWorker.Result {
    return this.fold(
        onSuccess = {
            Log.d("toWorkResult", "onSuccess")
            if (onSuccess(it)) {
                Log.d("toWorkResult", "Success with output")
                val outputData = Data.Builder()
                    .putBoolean(outputDataKey, true)
                    .build()
                ListenableWorker.Result.success(outputData)
            } else {
                Log.e("toWorkResult", "Error in writing file")
                ListenableWorker.Result.failure()
            }
        },
        onFailure = {
            Log.e("toWorkResult", "Error in $onFailureMessage", it)
            ListenableWorker.Result.failure()
        }
    )
}