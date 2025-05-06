package com.sibers.pokemon_list.domain.usecase

interface GetPokemonCountUseCase {
    suspend operator fun invoke(): Int
}