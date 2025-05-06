package com.sibers.pokemon_detail.data.usecaseimpl

import com.sibers.domain.entity.Pokemon
import com.sibers.domain.repository.PokemonRepository
import com.sibers.pokemon_detail.domain.usecase.GetPokemonDetailsUseCase
import javax.inject.Inject

class GetPokemonDetailsUseCaseImpl @Inject constructor(
    private val repo: PokemonRepository
) : GetPokemonDetailsUseCase {
    override suspend fun invoke(id: Int): Pokemon? = repo.getPokemon(id)
}