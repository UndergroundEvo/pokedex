package com.sibers.pokemon_list.data.usecaseimpl

import androidx.paging.PagingData
import com.sibers.domain.SortCriteria
import com.sibers.domain.entity.Pokemon
import com.sibers.domain.repository.PokemonRepository
import com.sibers.pokemon_list.domain.usecase.GetPokemonPagedUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonPagedUseCaseImpl @Inject constructor(
    private val repo: PokemonRepository
) : GetPokemonPagedUseCase {
    override fun invoke(sort: SortCriteria): Flow<PagingData<Pokemon>> = repo.getPokemonsPaged(sort)
}