package com.sibers.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.sibers.common.Utills.extractIdFromUrl
import com.sibers.datasource.ItemDatabaseSource
import com.sibers.datasource.ItemNetworkSource
import com.sibers.domain.SortCriteria
import com.sibers.domain.entity.Pokemon
import com.sibers.domain.repository.ConnectivityChecker
import com.sibers.domain.repository.PokemonRepository
import com.sibers.model.PokemonResourceResultDto
import com.sibers.network.mapper.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val networkSource: ItemNetworkSource,
    private val databaseSource: ItemDatabaseSource,
    private val connectivityChecker: ConnectivityChecker,
) : PokemonRepository {

    override suspend fun getTotalPokemonCount(): Int {
       return databaseSource.getCount()
    }

    override fun GetPokemonPagedOffset(
        sort: SortCriteria,
        initialKey: Int?
    ): Flow<PagingData<Pokemon>> {
        return databaseSource.getPaginatedOffsetItems(initialKey)
    }

    override fun getPokemonsPaged(sort: SortCriteria): Flow<PagingData<Pokemon>> {
        return if (connectivityChecker.isOnline()) {
            networkSource.getPaginatedItems()
                .map { pagingDataDto ->
                    pagingDataDto.map { dto ->
                        fetchDetailsAndMapToUiModel(dto)
                    }
                }
        } else {
            databaseSource.getPaginatedItems()
                .map { pagingDataEntity ->
                    pagingDataEntity
                }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPokemon(id: Int): Pokemon? {
        return databaseSource.getPokemonById(id)
    }

    private suspend fun fetchDetailsAndMapToUiModel(dto: PokemonResourceResultDto): Pokemon {
        val pokemonId = extractIdFromUrl(dto.url) ?: 1

        return withContext(Dispatchers.IO) {
            val cachedEntity = databaseSource.getPokemonById(pokemonId)
            if (cachedEntity != null) {
                return@withContext cachedEntity
            }
            val detailsResult = networkSource.getItemDetails(dto.url)
            val pokemonDto = detailsResult
            databaseSource.insertPokemon(pokemonDto.toDomainModel())
            return@withContext pokemonDto.toDomainModel()
        }
    }

    override suspend fun isCacheEmpty(): Boolean {
        return withContext(Dispatchers.IO) {
            databaseSource.checkDbIsEmpty()
        }
    }
}