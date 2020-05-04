package com.chenriquevz.pokedex.model

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
data class PokemonEvolution (
    @field:SerializedName("id") val id: Int,
    @Embedded @field:SerializedName("chain") val chain: EvolutionChainDetail
)

data class EvolutionChainDetail (
    @field:SerializedName("species") val species: GeneralEntry,
    @field:SerializedName("evolves_to") val evolvesTo: PokemonEvolution?
)