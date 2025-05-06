package com.sibers.model.pokemon.types

import com.sibers.model.pokemon.stats.StatDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PokemonTypesDto(
    @SerialName("type")
    val type: StatDto,
    @SerialName("slot")
    val slot : Int
)