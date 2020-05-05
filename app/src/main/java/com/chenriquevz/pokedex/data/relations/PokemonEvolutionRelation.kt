package com.chenriquevz.pokedex.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.chenriquevz.pokedex.model.EvolutionChainFirst
import com.chenriquevz.pokedex.model.EvolutionChainSecond
import com.chenriquevz.pokedex.model.PokemonEvolution

class PokemonEvolutionRelation {

    @Embedded
    var pokemonSecond: PokemonEvolution? = null

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = EvolutionChainFirst::class
    )
    var pokemonChainFirst: List<PokemonFirstToSecondChain> = emptyList()

}

class PokemonFirstToSecondChain {

    @Embedded
    var pokemonFirst: EvolutionChainFirst? = null

    @Relation(
        parentColumn = "localID",
        entityColumn = "id"
    )
    var pokemonSecond: List<EvolutionChainSecond> = emptyList()

}
