package com.sibers.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.sibers.pokemon_detail.presentation.PokemonDetailsFragment
import com.sibers.pokemon_list.presentation.PokemonListFragment

object Screens {
    fun pokemonList() = FragmentScreen { PokemonListFragment() }
    fun pokemonDetails(id: String) = FragmentScreen {
        PokemonDetailsFragment.newInstance(id)
    }
}