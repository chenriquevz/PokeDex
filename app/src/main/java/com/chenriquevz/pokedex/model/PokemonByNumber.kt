package com.chenriquevz.pokedex.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.room.Entity

@Entity(primaryKeys = ["id"])
data class PokemonByNumber(
    val id: Int,
    val name: String,
    val url: String
)

data class PokemonByNumberPaged(
    val data: LiveData<PagedList<PokemonByNumber>>,
    val networkErrors: LiveData<String>
)