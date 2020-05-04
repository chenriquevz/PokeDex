package com.chenriquevz.pokedex.model

import com.google.gson.annotations.SerializedName

class PaginationByNumber (
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<GeneralEntry>
)

class PaginationByType (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("previous") val previous: String?,
    @SerializedName("pokemon") val results: List<PokemonList>
)

class GeneralEntry (
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String?
)

class PokemonList (
    @SerializedName("pokemon") val general: GeneralEntry
)


