package com.chenriquevz.pokedex.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.chenriquevz.pokedex.model.EvolutionChainFirst
import com.chenriquevz.pokedex.model.EvolutionChainSecond
import com.chenriquevz.pokedex.model.PokemonEvolution

data class PokemonEvolutionRelation(
    @Embedded
    val pokemonBase: PokemonEvolution? = null,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = EvolutionChainFirst::class
    )
    val pokemonChainFirst: List<PokemonFirstToSecondChain>  = emptyList()
)


data class PokemonFirstToSecondChain(
    @Embedded
    val pokemonFirst: EvolutionChainFirst? = null,

    @Relation(
        parentColumn = "localID",
        entityColumn = "id"
    )
    val pokemonSecond: List<EvolutionChainSecond>  = emptyList()

)
