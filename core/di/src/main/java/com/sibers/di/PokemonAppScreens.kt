package com.sibers.di

import com.github.terrakok.cicerone.androidx.FragmentScreen

interface PokemonAppScreens {
    fun pokemonList() : FragmentScreen
    fun pokemonDetails(id: String): FragmentScreen
}