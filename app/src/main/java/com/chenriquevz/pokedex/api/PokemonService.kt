package com.chenriquevz.pokedex.api

import com.chenriquevz.pokedex.model.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon/{id}")
    suspend fun searchNameOrNumber(
       @Path("id") nameOrNumber: String
    ): Response<PokemonGeneral>

    @GET("pokemon/?")
    suspend fun pokemonListByNumber(
        @Query("offset") offset: String,
        @Query("limit") limit: Int
    ): Response<PaginationByNumber>

    @GET("type/{id}")
    suspend fun pokemonListByType(
        @Path("id") type: String
    ): Response<PaginationByType>

    @GET("pokemon-species/{id}")
    suspend fun pokemonSpecies(
        @Path("id")species: String
    ): Response<PokemonSpecies>

    @GET("ability/{id}")
    suspend fun pokemonAbility(
        @Path("id")ability: String
    ): Response<PokemonAbility>

    @GET("evolution-chain/{id}")
    suspend fun pokemonEvolution(
        @Path("id") evolutionChain: String
    ): Response<PokemonEvolution>

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"

        fun create(): PokemonService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PokemonService::class.java)
        }
    }
}