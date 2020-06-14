package com.chenriquevz.pokedex.model

import com.google.gson.annotations.SerializedName

data class PaginationByNumber(
    @SerializedName("next") val next: String?,
    @SerializedName("results") val results: List<GeneralEntry>
)

data class PaginationByType (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("pokemon") val results: List<PokemonList>
)

data class GeneralEntry (
    @SerializedName("name") val nameGeneral: String,
    @SerializedName("url") val urlGeneral: String
)

data class PokemonList (
    @SerializedName("pokemon") val general: GeneralEntry
)

data class PaginationEvolution (
    @SerializedName("id") val id: Int,
    @SerializedName("chain") val chain: PaginationEvolutionChain
)

data class PaginationEvolutionChain(
    @SerializedName("species") val species: GeneralEntry,
    @SerializedName("evolves_to") val evolvesTo: List<PaginationEvolutionChain>? = null
)
