package com.chenriquevz.pokedex.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    primaryKeys = ["id"],
    indices = [
        Index("id", unique = true)]
)
data class PokemonSpecies (
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @Embedded @field:SerializedName("evolution_chain") val evolutionChain: EvolutionChain,
    @Embedded @field:SerializedName("varieties") val varieties: List<PokemonVarieties>
    )

data class EvolutionChain (
    @field:SerializedName("url") val url: String
)

@Entity(
    foreignKeys = [ForeignKey(
        entity = PokemonSpecies::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.CASCADE
    )], indices = [
        Index("id", unique = false)]
)
data class PokemonVarieties (
    val id: Int,
    @field:SerializedName("is_default") val isDefault: Boolean,
    @field:SerializedName("pokemon") val pokemonVariety: GeneralEntry
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var localID: Int = 0
}