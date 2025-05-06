package com.sibers.di

import com.sibers.domain.repository.PokemonRepository

interface PokemonProvider {
    fun providePokemonRepository(): PokemonRepository
}