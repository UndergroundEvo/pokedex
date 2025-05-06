package com.sibers.pokemon_detail.presentation.state

import com.sibers.domain.entity.Pokemon

sealed interface PokemonDetailsIntent {
    data class LoadDetails(val pokemonId: Int) : PokemonDetailsIntent
    data class DetailsLoaded(val pokemon: Pokemon) : PokemonDetailsIntent
    data class ErrorLoading(val throwable: Throwable) : PokemonDetailsIntent
    data object BackPressed : PokemonDetailsIntent
}