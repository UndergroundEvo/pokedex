package com.sibers.model.pokemon

import com.sibers.model.pokemon.sprites.PokemonSpritesDto
import com.sibers.model.pokemon.stats.PokemonStatsItemDto
import com.sibers.model.pokemon.types.PokemonTypesDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("height")
    val height: Int,
    @SerialName("weight")
    val weight: Int,
    @SerialName("sprites")
    val spirites: PokemonSpritesDto,
    @SerialName("stats")
    val stats: List<PokemonStatsItemDto>,
    @SerialName("types")
    val types: List<PokemonTypesDto>
    /*@SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("height") val height: Int,
    @SerialName("weight") val weight: Int,
    @SerialName("types") val types: PokemonTypesDto,
    @SerialName("stats") val stats: List<PokemonStatsItemDto>,
    @SerialName("sprites") val sprites: PokemonSpritesDto,*/
)
