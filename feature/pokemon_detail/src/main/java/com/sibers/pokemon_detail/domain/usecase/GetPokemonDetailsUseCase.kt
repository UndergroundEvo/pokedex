package com.sibers.pokemon_detail.domain.usecase

import com.sibers.domain.entity.Pokemon

interface GetPokemonDetailsUseCase {
    suspend operator fun invoke(id: Int) : Pokemon?
}