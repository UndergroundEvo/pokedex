package com.sibers.pokemon

import android.app.Application
import com.sibers.di.App
import com.sibers.di.DependenciesProvider

class PokemonApp : Application(), App {

    private val component by lazy { AppComponent.create(applicationContext) }

    override fun getDependenciesProvider(): DependenciesProvider {
        return component
    }
}