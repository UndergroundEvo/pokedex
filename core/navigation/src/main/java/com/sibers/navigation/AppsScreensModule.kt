package com.sibers.navigation

import com.sibers.di.PokemonAppScreens
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface AppsScreensModule {
    @Singleton
    @Binds
    fun bindNavigationScreens(impl: PokemonNavigationScreens): PokemonAppScreens
}

