package com.sibers.pokemon_detail.presentation.state

sealed interface PokemonDetailsSideEffect {
    data class ShowError(val message: String) : PokemonDetailsSideEffect
    data object NavigateBack : PokemonDetailsSideEffect
}