package com.sibers.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sibers.common.Const.POKEMON_TABLE
import com.sibers.database.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: PokemonEntity)

    @Delete
    fun delete(pokemon: PokemonEntity)

    @Query("SELECT * FROM $POKEMON_TABLE WHERE id = :id")
    suspend fun getPokemonById(id: Int): PokemonEntity?

    @Query("SELECT * FROM $POKEMON_TABLE LIMIT :limit OFFSET :offset")
    suspend fun getPokemonList(limit: Int, offset: Int): List<PokemonEntity>

    @Query("SELECT COUNT(*) FROM $POKEMON_TABLE")
    fun getCount(): Int
}