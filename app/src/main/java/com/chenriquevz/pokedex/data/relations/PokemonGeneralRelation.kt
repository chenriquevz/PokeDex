package com.chenriquevz.pokedex.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.chenriquevz.pokedex.model.AbilitiesList
import com.chenriquevz.pokedex.model.PokemonGeneral
import com.chenriquevz.pokedex.model.Stats
import com.chenriquevz.pokedex.model.Type

data class PokemonGeneralRelation(
    @Embedded
    val pokemonGeneral: PokemonGeneral,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val abilitiesList: List<AbilitiesList>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val stats: List<Stats>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val type: List<Type>
)