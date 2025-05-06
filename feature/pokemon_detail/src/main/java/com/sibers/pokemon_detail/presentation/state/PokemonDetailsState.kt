package com.sibers.pokemon_detail.presentation.state

import com.sibers.domain.entity.Pokemon

data class PokemonDetailsState(
    val pokemon: Pokemon? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val pokemonId: Int? = null
) {
    val isContentLoaded: Boolean
        get() = pokemon != null && !isLoading && error == null
    val isErrorVisible: Boolean
        get() = error != null && !isLoading
}