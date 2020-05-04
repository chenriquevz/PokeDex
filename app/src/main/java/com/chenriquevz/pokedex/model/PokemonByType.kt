package com.chenriquevz.pokedex.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
data class PokemonByType(
    val id: Int,
    val name: String,
    val url: String,
    val type_id: Int,
    val type_name: String
)