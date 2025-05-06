package com.sibers.domain.usecase

import com.sibers.domain.entity.Pokemon

interface FetchPokemonDetailsUseCase {
    suspend operator fun invoke() : Result<Pokemon>
}