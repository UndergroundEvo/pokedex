package com.sibers.pokemon_list.di

import androidx.lifecycle.ViewModel
import com.sibers.pokemon_list.presentation.PokemonListViewModel
import com.sibers.ui.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PokemonListViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PokemonListViewModel::class)
    fun bindPokemonListViewModel(viewModel: PokemonListViewModel): ViewModel
}