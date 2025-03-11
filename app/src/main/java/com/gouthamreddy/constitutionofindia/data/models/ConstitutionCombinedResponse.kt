package com.gouthamreddy.constitutionofindia.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConstitutionCombinedResponseItem(
    @SerialName("content")
    val content: Content = Content(),
    @SerialName("part")
    val part: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("url")
    val url: String = ""
) {
    @Serializable
    data class Content(
        @SerialName("article_number")
        val articleNumber: String = "",
        @SerialName("intro_content")
        val introContent: String = "",
        @SerialName("summary")
        val summary: String = "",
        @SerialName("title")
        val title: String = "",
        @SerialName("version_1")
        val version1: String = "",
        @SerialName("version_2")
        val version2: String = ""
    )
}