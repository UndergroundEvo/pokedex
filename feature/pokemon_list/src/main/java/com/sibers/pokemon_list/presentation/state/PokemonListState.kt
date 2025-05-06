package com.sibers.pokemon_list.presentation.state

import androidx.paging.PagingData
import com.sibers.domain.SortCriteria
import com.sibers.domain.entity.Pokemon
import com.sibers.pokemon_list.presentation.filter.FilterCriteria

data class PokemonListState(
    val pagingData: PagingData<Pokemon> = PagingData.empty(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val currentFilter: FilterCriteria = FilterCriteria(),
    val currentSort: SortCriteria = SortCriteria.ID_ASC,
    val highlightedPokemonInHeader: Pokemon? = null,
    val randomInitialKey: Int? = null
)
