package com.sibers.pokemon_list.di

import com.sibers.di.DependenciesProvider
import com.sibers.pokemon_list.presentation.PokemonListFragment
import com.sibers.ui.di.viewmodel.ViewModelFactoryModule
import dagger.Component

@Component(
    dependencies = [DependenciesProvider::class],
    modules = [
        ViewModelFactoryModule::class,
        PokemonListViewModelModule::class,
        PokemonListUseCaseModule::class,
    ]
)
interface PokemonListComponent {
    companion object {

        fun create(dependenciesProvider: DependenciesProvider): PokemonListComponent {
            return DaggerPokemonListComponent.factory()
                .create(dependenciesProvider)
        }
    }

    fun inject(favoriteListFragment: PokemonListFragment)

    @Component.Factory
    interface Factory {
        fun create(
            dependenciesProvider: DependenciesProvider,
        ): PokemonListComponent
    }
}