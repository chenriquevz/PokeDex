package com.chenriquevz.pokedex.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    primaryKeys = ["id"],
    indices = [
        Index("id", unique = true)]
)
data class PokemonGeneral(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("is_default") val isDefaault: Boolean,
    @field:SerializedName("abilities") val abilities: List<AbilitiesList>,
    @Embedded @field:SerializedName("species") val species: GeneralEntry,
    @Embedded @field:SerializedName("sprites") val sprites: Sprites,
    @field:SerializedName("stats") val stats: List<Stats>,
    @field:SerializedName("types") val type: List<Type>
    )

@Entity(
    foreignKeys = [ForeignKey(
        entity = PokemonGeneral::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.CASCADE
    )], indices = [
        Index("id", unique = false)]
)
data class AbilitiesList (
    val id: Int,
    @field:SerializedName("ability") val general: GeneralEntry
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var localID: Int = 0
}

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

@Entity(
    foreignKeys = [ForeignKey(
        entity = PokemonGeneral::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.CASCADE
    )], indices = [
        Index("id", unique = false)]
)
data class Stats (
    val id: Int,
    @field:SerializedName("base_stat") val baseStat: Int,
    @field:SerializedName("effort") val effort: Int,
    @Embedded @field:SerializedName("stat") val stat: GeneralEntry
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var localID: Int = 0
}


@Entity(
    foreignKeys = [ForeignKey(
        entity = PokemonGeneral::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.CASCADE
    )], indices = [
        Index("id", unique = false)]
)
data class Type (
    val id: Int,
    @field:SerializedName("type") val baseStat: GeneralEntry
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var localID: Int = 0
}