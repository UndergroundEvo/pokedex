package com.sibers.pokemon

import android.content.Context
import com.sibers.data.di.DataComponent
import com.sibers.di.DependenciesProvider
import com.sibers.di.PokemonProvider
import com.sibers.navigation.AppsScreensModule
import com.sibers.navigation.NavigationModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [PokemonProvider::class],
    modules = [
        NavigationModule::class,
        AppsScreensModule::class
    ]
)
interface AppComponent: DependenciesProvider {
    companion object {

        fun create(context: Context): AppComponent {
            val dataProvider = DataComponent.create(context)
            return DaggerAppComponent.factory()
                .create(context, dataProvider)
        }
    }

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Builder {
        fun create(
            @BindsInstance context: Context,
            dataProvider: PokemonProvider,
        ): AppComponent
    }
}