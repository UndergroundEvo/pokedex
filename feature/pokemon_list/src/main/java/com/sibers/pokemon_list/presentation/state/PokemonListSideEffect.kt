package com.sibers.pokemon_list.presentation.state

import com.sibers.domain.SortCriteria

sealed interface PokemonListSideEffect {
    data object ShowFilterSheet : PokemonListSideEffect
    data class ShowSortSheet(val currentSort: SortCriteria) : PokemonListSideEffect
    data class ScrollToAndHighlight(val pokemonId: Int) : PokemonListSideEffect
}