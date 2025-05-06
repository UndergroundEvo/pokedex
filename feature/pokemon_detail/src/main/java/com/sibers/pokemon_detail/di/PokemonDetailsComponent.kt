package com.sibers.pokemon_detail.di

import com.sibers.di.DependenciesProvider
import com.sibers.pokemon_detail.presentation.PokemonDetailsFragment
import com.sibers.ui.di.viewmodel.ViewModelFactoryModule
import dagger.Component

@Component(
    dependencies = [DependenciesProvider::class],
    modules = [
        ViewModelFactoryModule::class,
        PokemonDetailsViewModelModule::class,
        PokemonDetailsUseCaseModule::class,
    ]
)
interface PokemonDetailsComponent {
    companion object {

        fun create(dependenciesProvider: DependenciesProvider): PokemonDetailsComponent {
            return DaggerPokemonDetailsComponent.factory()
                .create(dependenciesProvider)
        }
    }

    fun inject(favoriteListFragment: PokemonDetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            dependenciesProvider: DependenciesProvider,
        ): PokemonDetailsComponent
    }
}