package com.sibers.model.pokemon.sprites


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OtherDto(
    @SerialName("official-artwork")
    val officialArtworkDto: OfficialArtworkDto,
)