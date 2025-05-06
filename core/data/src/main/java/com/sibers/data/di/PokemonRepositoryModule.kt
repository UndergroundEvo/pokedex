package com.sibers.data.di

import com.sibers.data.repository.PokemonRepositoryImpl
import com.sibers.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module

@Module
abstract class PokemonRepositoryModule {
    @Binds
    abstract fun bindPokemonRepository(repositoryImpl: PokemonRepositoryImpl): PokemonRepository
}