package com.sibers.network.service

import com.sibers.model.PokemonResourceListsDto
import com.sibers.model.pokemon.PokemonDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonService {
    @GET("pokemon")
    suspend fun getResourceList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonResourceListsDto

    @GET
    suspend fun getPokemonByUrl(@Url url: String): PokemonDto
}