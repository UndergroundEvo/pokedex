package com.sibers.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResourceResultDto(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)
