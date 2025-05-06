package com.sibers.network.mapper

import com.sibers.domain.entity.Pokemon
import com.sibers.model.pokemon.PokemonDto

fun PokemonDto.toDomainModel(): Pokemon {
    return Pokemon(
        id = this.id,
        name = this.name,
        height = this.height,
        weight = this.weight,
        image = this.spirites.otherDto.officialArtworkDto.frontDefault,
        type = this.types.map { it.type.name },
        stats = this.stats.associate { it.statDto.name to it.baseStat }
    )
}