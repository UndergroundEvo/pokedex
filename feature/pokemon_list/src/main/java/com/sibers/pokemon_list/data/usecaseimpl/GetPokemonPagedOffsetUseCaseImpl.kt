package com.sibers.pokemon_list.data.usecaseimpl

import androidx.paging.PagingData
import com.sibers.domain.SortCriteria
import com.sibers.domain.entity.Pokemon
import com.sibers.domain.repository.PokemonRepository
import com.sibers.pokemon_list.domain.usecase.GetPokemonPagedOffsetUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonPagedOffsetUseCaseImpl @Inject constructor(
    private val repository: PokemonRepository
) : GetPokemonPagedOffsetUseCase {
    override operator fun invoke(sort: SortCriteria, initialKey: Int?): Flow<PagingData<Pokemon>> {
        return repository.GetPokemonPagedOffset(sort,initialKey)
    }
}