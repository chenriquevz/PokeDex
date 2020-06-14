package com.chenriquevz.pokedex.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.chenriquevz.pokedex.model.PokemonSpecies
import com.chenriquevz.pokedex.model.PokemonVarieties

data class PokemonSpeciesRelation(
    @Embedded
    val pokemonSpecies: PokemonSpecies,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val pokemonVarieties: List<PokemonVarieties>
)