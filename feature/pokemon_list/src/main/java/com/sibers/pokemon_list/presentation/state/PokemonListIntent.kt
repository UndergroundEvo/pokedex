package com.sibers.pokemon_list.presentation.state

import com.sibers.domain.SortCriteria
import com.sibers.domain.entity.Pokemon
import com.sibers.pokemon_list.presentation.filter.FilterCriteria

sealed interface PokemonListIntent{
    data object LoadData : PokemonListIntent
    data object ReloadFromRandom : PokemonListIntent
    data object OpenFilterSheet : PokemonListIntent
    data object OpenSortSheet : PokemonListIntent
    data class ApplyFilter(val criteria: FilterCriteria) : PokemonListIntent
    data class ApplySort(val criteria: SortCriteria) : PokemonListIntent
    data class PokemonClicked(val pokemonId: Long) : PokemonListIntent
    data class ProcessFilterOnList(val list: List<Pokemon>, val criteria: FilterCriteria) : PokemonListIntent
}