package com.sibers.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sibers.database.converters.PokemonDbConverters
import com.sibers.database.dao.PokemonDao
import com.sibers.database.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
@TypeConverters(PokemonDbConverters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao() : PokemonDao
}