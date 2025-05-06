package com.sibers.datasource

import androidx.paging.PagingData
import com.sibers.domain.entity.Pokemon
import kotlinx.coroutines.flow.Flow

interface ItemDatabaseSource {
    suspend fun getCount(): Int
    fun getPaginatedOffsetItems(initialKey: Int?): Flow<PagingData<Pokemon>>
    fun getPaginatedItems(): Flow<PagingData<Pokemon>>
    suspend fun getPokemonById(id: Int): Pokemon?
    suspend fun insertPokemon(pokemon: Pokemon)
    fun checkDbIsEmpty(): Boolean
}