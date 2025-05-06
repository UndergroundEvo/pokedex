package com.sibers.network.di

import com.sibers.common.Const
import com.sibers.datasource.ItemNetworkSource
import com.sibers.network.datasource.ItemNetworkSourceImpl
import com.sibers.network.service.PokemonService
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton


@Module(includes = [NetworkModule.Providers::class])
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindNetworkDataSource(impl: ItemNetworkSourceImpl): ItemNetworkSource

    @Module
    object Providers {
        @Provides
        @Singleton
        fun provideJson(): Json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        @Singleton
        @Provides
        fun provideOkHttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
            val contentType = "application/json".toMediaType()
            return Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
        }

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): PokemonService =
            retrofit.create(PokemonService::class.java)
    }
}