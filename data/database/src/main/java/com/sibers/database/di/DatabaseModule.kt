package com.sibers.database.di

import android.content.Context
import androidx.room.Room
import com.sibers.common.Const
import com.sibers.database.dao.PokemonDao
import com.sibers.database.database.PokemonDatabase
import com.sibers.database.datasource.ItemDatabaseSourceImpl
import com.sibers.datasource.ItemDatabaseSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule.Providers::class])
abstract class DatabaseModule {

    @Module
    object Providers {
        @Provides
        @Singleton
        fun provideDatabase(context: Context): PokemonDatabase = Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            provideDatabaseName()
        ).build()

        @Provides
        fun provideDatabaseName(): String = Const.DATABASE_NAME


        @Provides
        @Singleton
        fun providePokemonDao(db: PokemonDatabase): PokemonDao = db.pokemonDao()
    }

    @Binds
    @Singleton
    abstract fun bindNetworkDataSource(impl: ItemDatabaseSourceImpl): ItemDatabaseSource
}