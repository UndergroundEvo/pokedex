package com.sibers.di

import com.github.terrakok.cicerone.Router

interface DependenciesProvider : PokemonProvider{
    fun provideRouter(): Router
    fun provideAppScreen() : PokemonAppScreens
}