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
        entityColumn = "id"
    )
    var pokemonChainSecond: List<PokemonFirstToSecondChain> = emptyList()

}

class PokemonFirstToSecondChain {

    @Embedded
    var pokemonSecond: EvolutionChainFirst? = null

    @Relation(
        parentColumn = "localID",
        entityColumn = "id"
    )
    var pokemonThird: List<EvolutionChainSecond> = emptyList()

}
