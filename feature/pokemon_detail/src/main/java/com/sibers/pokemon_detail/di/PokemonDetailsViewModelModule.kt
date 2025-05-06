package com.sibers.pokemon_detail.di

import androidx.lifecycle.ViewModel
import com.sibers.pokemon_detail.presentation.PokemonDetailsViewModel
import com.sibers.ui.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PokemonDetailsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PokemonDetailsViewModel::class)
    fun bindPokemonListViewModel(viewModel: PokemonDetailsViewModel): ViewModel
}