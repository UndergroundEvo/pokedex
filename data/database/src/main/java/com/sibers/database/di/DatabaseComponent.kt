package com.sibers.database.di

import android.content.Context
import com.sibers.di.DatabaseProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DatabaseModule::class]
)
interface DatabaseComponent : DatabaseProvider {
    companion object{
        fun create(context: Context): DatabaseComponent{
            return DaggerDatabaseComponent.factory()
                .create(context)
        }
    }
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
        ): DatabaseComponent
    }
}