package com.sibers.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.sibers.di.PokemonAppScreens
import com.sibers.pokemon_detail.presentation.PokemonDetailsFragment
import com.sibers.pokemon_list.presentation.PokemonListFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonNavigationScreens @Inject constructor() : PokemonAppScreens {
    override fun pokemonList() = FragmentScreen { PokemonListFragment() }
    override fun pokemonDetails(id: String) = FragmentScreen {
        PokemonDetailsFragment.newInstance(id)
    }
}