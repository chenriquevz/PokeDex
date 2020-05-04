package com.chenriquevz.pokedex.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.chenriquevz.pokedex.model.AbilityEffectEntries
import com.chenriquevz.pokedex.model.PokemonAbility

class PokemonAbilityRelation {

    @Embedded
    var pokemonAbility: PokemonAbility? = null

    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    var abilityEffectEntries: List<AbilityEffectEntries> = emptyList()

}