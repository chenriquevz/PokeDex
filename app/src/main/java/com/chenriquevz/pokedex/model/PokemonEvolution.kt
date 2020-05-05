package com.chenriquevz.pokedex.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    primaryKeys = ["id"],
    indices = [
        Index("id", unique = true)]
)
data class PokemonEvolution(
    @field:SerializedName("id") val id: Int,
    @Embedded @field:SerializedName("species") val species: GeneralEntry
) {
    @Ignore
    @field:SerializedName("chain")
    var chain: EvolutionChainFirst? = null
}

@Entity(
    foreignKeys = [ForeignKey(
        entity = PokemonEvolution::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.CASCADE
    )], indices = [
        Index("id", unique = true)]
)
data class EvolutionChainFirst(
    val id: Int,
    @PrimaryKey val localID: Int,
    @Embedded @field:SerializedName("species") val species: GeneralEntry
) {
    @Ignore
    @field:SerializedName("evolves_to")
    var evolvesTo: List<EvolutionChainSecond>? = null
}

@Entity(
    foreignKeys = [ForeignKey(
        entity = EvolutionChainFirst::class,
        parentColumns = arrayOf("localID"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.CASCADE
    )], indices = [
        Index("id", unique = false)]
)
data class EvolutionChainSecond(
    val id: Int,
    @Embedded @field:SerializedName("species") val species: GeneralEntry
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "localID")
    var localID: Int = 0
}
