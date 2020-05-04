package com.chenriquevz.pokedex.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
data class PokemonByNumber(
    val id: Int,
    val name: String,
    val url: String
)