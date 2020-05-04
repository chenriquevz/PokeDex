package com.chenriquevz.pokedex.model

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
data class PokemonSpecies (
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @Embedded @field:SerializedName("evolution_chain") val evolutionChain: EvolutionChain,
    @Embedded @field:SerializedName("varieties") val varieties: List<PokemonVarieties>
    )

data class EvolutionChain (
    @field:SerializedName("url") val url: String
)

data class PokemonVarieties (
    @field:SerializedName("is_default") val isDefault: Boolean,
    @field:SerializedName("pokemon") val pokemonVariety: GeneralEntry
)