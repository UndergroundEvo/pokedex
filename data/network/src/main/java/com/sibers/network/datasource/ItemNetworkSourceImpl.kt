package com.sibers.network.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sibers.common.Const
import com.sibers.datasource.ItemNetworkSource
import com.sibers.model.PokemonResourceResultDto
import com.sibers.model.pokemon.PokemonDto
import com.sibers.network.service.PokemonService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemNetworkSourceImpl @Inject constructor(
    private val pokemonService: PokemonService
) : ItemNetworkSource {

    override fun getPaginatedItems(): Flow<PagingData<PokemonResourceResultDto>> {
        return Pager(
            config = PagingConfig(pageSize = Const.PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = { PokemonNetworkPagingSource(pokemonService, Const.PAGE_SIZE) }
        ).flow
    }

    override suspend fun getItemDetails(url: String): PokemonDto {
        return pokemonService.getPokemonByUrl(url)
    }
}