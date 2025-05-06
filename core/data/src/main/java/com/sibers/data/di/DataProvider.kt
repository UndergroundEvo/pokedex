package com.sibers.data.di

import com.sibers.domain.repository.PokemonRepository

interface DataProvider {
    fun providesPokemonRepository() : PokemonRepository
}