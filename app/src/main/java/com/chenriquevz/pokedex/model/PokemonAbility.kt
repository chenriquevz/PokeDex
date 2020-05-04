package com.chenriquevz.pokedex.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
data class PokemonAbility (
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("effect_entries") val effectEntries: List<AbilityEffectEntries>
)

data class AbilityEffectEntries(
    @field:SerializedName("effect") val effect: String,
    @field:SerializedName("short_effect") val shortEffect: String
)
