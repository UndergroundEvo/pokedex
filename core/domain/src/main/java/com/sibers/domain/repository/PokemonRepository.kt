package com.sibers.domain.repository

import androidx.paging.PagingData
import com.sibers.domain.SortCriteria
import com.sibers.domain.entity.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getTotalPokemonCount(): Int
    fun GetPokemonPagedOffset(sort: SortCriteria, initialKey: Int?): Flow<PagingData<Pokemon>>
    fun getPokemonsPaged(sort: SortCriteria): Flow<PagingData<Pokemon>>
    suspend fun getPokemon(id: Int): Pokemon?
    suspend fun isCacheEmpty(): Boolean
}