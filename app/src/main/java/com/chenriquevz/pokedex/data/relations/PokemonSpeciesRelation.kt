package com.chenriquevz.pokedex.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.chenriquevz.pokedex.model.PokemonSpecies
import com.chenriquevz.pokedex.model.PokemonVarieties

class PokemonSpeciesRelation {

    @Embedded
    var pokemonSpecies: PokemonSpecies? = null

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    var pokemonVarieties: List<PokemonVarieties> = emptyList()


}