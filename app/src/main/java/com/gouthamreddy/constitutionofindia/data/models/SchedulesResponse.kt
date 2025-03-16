package com.gouthamreddy.constitutionofindia.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SchedulesResponse(
    @SerialName("Articles")
    val articles: String = "",
    @SerialName("Details")
    val details: List<Detail> = listOf(),
    @SerialName("Title")
    val title: String = ""
) {
    @Serializable
    data class Detail(
        @SerialName("Text")
        val text: String = "",
        @SerialName("URL")
        val url: String = ""
    )
}
