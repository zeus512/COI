package com.gouthamreddy.constitutionofindia.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreambleResponse(
    @SerialName("Preamble")
    val content: String = "",
)