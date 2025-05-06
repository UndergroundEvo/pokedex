package com.sibers.pokemon_list.domain.usecase

import androidx.paging.PagingData
import com.sibers.domain.SortCriteria
import com.sibers.domain.entity.Pokemon
import kotlinx.coroutines.flow.Flow

interface GetPokemonPagedUseCase {
    operator fun invoke(sort: SortCriteria) : Flow<PagingData<Pokemon>>
}