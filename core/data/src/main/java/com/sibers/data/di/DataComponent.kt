package com.sibers.data.di

import android.content.Context
import com.sibers.database.di.DatabaseComponent
import com.sibers.di.DatabaseProvider
import com.sibers.di.NetworkProvider
import com.sibers.di.PokemonProvider
import com.sibers.network.di.NetworkComponent
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [
        DatabaseProvider::class,
        NetworkProvider::class
    ],
    modules = [
        PokemonRepositoryModule::class,
        ConnectivityCheckerModule::class,
    ]
)
interface DataComponent : PokemonProvider {
    companion object {
        fun create(context : Context): DataComponent {
            val networkProvider = NetworkComponent.create()
            val databaseProvider = DatabaseComponent.create(context)
            return DaggerDataComponent.factory()
                .create(context, networkProvider, databaseProvider)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            networkProvider: NetworkProvider,
            databaseProvider: DatabaseProvider
        ): DataComponent
    }
}