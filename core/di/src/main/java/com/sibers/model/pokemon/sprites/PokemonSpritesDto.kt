package com.sibers.model.pokemon.sprites


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpritesDto(
    @SerialName("other")
    val otherDto: OtherDto,
)