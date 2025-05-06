package com.sibers.network.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sibers.model.PokemonResourceResultDto
import com.sibers.network.service.PokemonService
import retrofit2.HttpException
import java.io.IOException

class PokemonNetworkPagingSource(
    private val pokemonService: PokemonService,
    private val count: Int
) : PagingSource<Int, PokemonResourceResultDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResourceResultDto> {
        val offset = params.key ?: 0
        return try {
            val response = pokemonService.getResourceList(limit = count, offset = offset)
            val items = response.results

            LoadResult.Page(
                data = items,
                prevKey = if (offset == 0) null else offset - count,
                nextKey = if (items.isEmpty()) null else offset + count
            )
        } catch (e: IOException) { LoadResult.Error(e) }
        catch (e: HttpException) { LoadResult.Error(e) }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonResourceResultDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(count)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(count)
        }
    }
}