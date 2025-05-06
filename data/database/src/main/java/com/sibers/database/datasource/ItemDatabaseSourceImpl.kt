package com.sibers.database.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sibers.common.Const
import com.sibers.database.dao.PokemonDao
import com.sibers.database.mapper.toDomainModel
import com.sibers.database.mapper.toEntityModel
import com.sibers.datasource.ItemDatabaseSource
import com.sibers.domain.entity.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ItemDatabaseSourceImpl @Inject constructor(
    private val dao: PokemonDao
) : ItemDatabaseSource {
    override suspend fun getCount(): Int {
        return dao.getCount()
    }

    override fun getPaginatedOffsetItems(initialKey: Int?): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = Const.PAGE_SIZE),
            pagingSourceFactory = { PokemonDatabasePagingSourceOffseted(dao, Const.PAGE_SIZE, initialKey ?: 0) }
        ).flow.map { it.map { it.toDomainModel() } }
    }


    override fun getPaginatedItems(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = Const.PAGE_SIZE),
            pagingSourceFactory = { PokemonDatabasePagingSource(dao, Const.PAGE_SIZE) }
        ).flow.map { it.map { it.toDomainModel() } }
    }

    override suspend fun getPokemonById(id: Int): Pokemon? {
        return dao.getPokemonById(id)?.toDomainModel()
    }

    override suspend fun insertPokemon(pokemon: Pokemon) = dao.insert(pokemon.toEntityModel())

    override fun checkDbIsEmpty() = dao.getCount() == 0
}