package com.chenriquevz.pokedex.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.chenriquevz.pokedex.model.AbilityEffectEntries
import com.chenriquevz.pokedex.model.PokemonAbility

data class PokemonAbilityRelation(
    @Embedded
    val pokemonAbility: PokemonAbility? = null,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val abilityEffectEntries: List<AbilityEffectEntries?>? = null
)