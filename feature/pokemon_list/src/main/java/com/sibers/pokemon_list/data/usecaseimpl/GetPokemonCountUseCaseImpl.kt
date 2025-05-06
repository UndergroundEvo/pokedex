package com.sibers.pokemon_list.data.usecaseimpl

import com.sibers.domain.repository.PokemonRepository
import com.sibers.pokemon_list.domain.usecase.GetPokemonCountUseCase
import javax.inject.Inject

class GetPokemonCountUseCaseImpl @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : GetPokemonCountUseCase {
    override suspend operator fun invoke(): Int {
        return pokemonRepository.getTotalPokemonCount()
    }
}