package com.sibers.database.mapper

import com.sibers.database.entity.PokemonEntity
import com.sibers.domain.entity.Pokemon

fun PokemonEntity.toDomainModel() = Pokemon(
    id = this.id,
    name = this.name,
    height = this.height,
    image = this.image,
    type = this.type,
    weight = this.weight,
    stats = this.stats
)


fun Pokemon.toEntityModel() = PokemonEntity(
    id = this.id,
    name = this.name,
    height = this.height,
    image = this.image,
    type = this.type,
    stats = this.stats,
    weight = this.weight
)
