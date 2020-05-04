package com.chenriquevz.pokedex.model

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
data class PokemonGeneral(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("is_default") val isDefaault: Boolean,
    @Embedded @field:SerializedName("abilities") val abilities: List<AbilitiesList>,
    @Embedded @field:SerializedName("species") val species: GeneralEntry,
    @Embedded @field:SerializedName("sprites") val sprites: Sprites,
    @Embedded @field:SerializedName("stats") val stats: Stats,
    @Embedded @field:SerializedName("types") val type: List<Type>
    )

data class AbilitiesList (
    @field:SerializedName("ability") val general: GeneralEntry
)

data class Sprites (
    @field:SerializedName("front_default") val frontDefault: String?,
    @field:SerializedName("back_default") val backDefault: String?,
    @field:SerializedName("front_shiny") val frontShiny: String?,
    @field:SerializedName("back_shiny") val backShiny: String?,
    @field:SerializedName("front_female") val frontFemale: String?,
    @field:SerializedName("back_female") val backFemale: String?,
    @field:SerializedName("front_shiny_female") val frontShinyFemale: String?,
    @field:SerializedName("back_shiny_female") val backShinyFemale: String?
)

data class Stats (
    @field:SerializedName("base_stat") val baseStat: Int,
    @field:SerializedName("effort") val effort: Int,
    @field:SerializedName("stat") val frontShiny: List<GeneralEntry>
)

data class Type (
    @field:SerializedName("type") val baseStat: GeneralEntry
)