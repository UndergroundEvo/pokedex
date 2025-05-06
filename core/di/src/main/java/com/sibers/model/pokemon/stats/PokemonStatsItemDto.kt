package com.sibers.model.pokemon.stats


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonStatsItemDto(
    @SerialName("base_stat")
    val baseStat: Int,
    @SerialName("effort")
    val effort: Int,
    @SerialName("stat")
    val statDto: StatDto
)