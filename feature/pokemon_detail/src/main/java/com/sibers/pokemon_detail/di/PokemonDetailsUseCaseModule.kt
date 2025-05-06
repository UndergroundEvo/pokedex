package com.sibers.pokemon_detail.di

import com.sibers.pokemon_detail.data.usecaseimpl.GetPokemonDetailsUseCaseImpl
import com.sibers.pokemon_detail.domain.usecase.GetPokemonDetailsUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class PokemonDetailsUseCaseModule{
    @Binds
    abstract fun bindGetPokemonPagedUseCase(
        impl: GetPokemonDetailsUseCaseImpl
    ): GetPokemonDetailsUseCase
}