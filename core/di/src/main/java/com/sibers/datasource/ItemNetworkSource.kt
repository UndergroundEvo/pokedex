package com.sibers.datasource

import androidx.paging.PagingData
import com.sibers.model.PokemonResourceResultDto
import com.sibers.model.pokemon.PokemonDto
import kotlinx.coroutines.flow.Flow

interface ItemNetworkSource {
    fun getPaginatedItems(): Flow<PagingData<PokemonResourceResultDto>>
    suspend fun getItemDetails(url: String): PokemonDto
}