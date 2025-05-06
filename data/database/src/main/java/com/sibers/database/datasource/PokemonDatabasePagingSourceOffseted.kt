package com.sibers.database.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sibers.database.dao.PokemonDao
import com.sibers.database.entity.PokemonEntity

class PokemonDatabasePagingSourceOffseted(
    private val dao: PokemonDao,
    private val count: Int,
    private val offset: Int
) : PagingSource<Int, PokemonEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonEntity> {
        val offset = params.key ?: offset
        return try {
            val data = dao.getPokemonList(limit = count, offset = offset)
            LoadResult.Page(
                data = data,
                prevKey = if (offset == 0) null else offset - count,
                nextKey = if (data.isEmpty()) null else offset + count
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(count)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(count)
        }
    }
}