package com.chenriquevz.pokedex.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.chenriquevz.pokedex.model.AbilitiesList
import com.chenriquevz.pokedex.model.PokemonGeneral
import com.chenriquevz.pokedex.model.Stats
import com.chenriquevz.pokedex.model.Type

class PokemonGeneralRelation {

    @Embedded
    var pokemonGeneral: PokemonGeneral? = null

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    var abilitiesList: List<AbilitiesList> = emptyList()

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    var stats: List<Stats> = emptyList()

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    var type: List<Type> = emptyList()

}