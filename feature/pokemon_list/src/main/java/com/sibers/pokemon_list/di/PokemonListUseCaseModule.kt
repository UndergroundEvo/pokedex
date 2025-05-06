package com.sibers.pokemon_list.di

import com.sibers.pokemon_list.data.usecaseimpl.GetPokemonCountUseCaseImpl
import com.sibers.pokemon_list.data.usecaseimpl.GetPokemonPagedOffsetUseCaseImpl
import com.sibers.pokemon_list.data.usecaseimpl.GetPokemonPagedUseCaseImpl
import com.sibers.pokemon_list.domain.usecase.GetPokemonCountUseCase
import com.sibers.pokemon_list.domain.usecase.GetPokemonPagedOffsetUseCase
import com.sibers.pokemon_list.domain.usecase.GetPokemonPagedUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class PokemonListUseCaseModule {
    @Binds
    abstract fun bindGetPokemonPagedUseCase(
        impl: GetPokemonPagedUseCaseImpl
    ): GetPokemonPagedUseCase

    @Binds
    abstract fun bindGetPokemonPagedOffsetUseCase(
        impl: GetPokemonPagedOffsetUseCaseImpl
    ): GetPokemonPagedOffsetUseCase

    @Binds
    abstract fun bindGetPokemonCountUseCase(
        impl: GetPokemonCountUseCaseImpl
    ): GetPokemonCountUseCase



}