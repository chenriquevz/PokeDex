package com.chenriquevz.pokedex.model


import androidx.room.*
import com.chenriquevz.pokedex.utils.replaceDashCapitalizeWords
import com.google.gson.annotations.SerializedName

@Entity(
    primaryKeys = ["id"],
    indices = [
        Index("id", unique = true)]
)
data class PokemonSpecies(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @Embedded @field:SerializedName("evolution_chain") val evolutionChain: EvolutionChain
) {
    @Ignore
    @field:SerializedName("varieties")
    var varieties: List<PokemonVarieties> = emptyList()
}

data class EvolutionChain(
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
data class PokemonVarieties(
    val id: Int,
    @field:SerializedName("is_default") val isDefault: Boolean,
    @Embedded @field:SerializedName("pokemon") val pokemonVariety: GeneralEntry
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "localID")
    var localID: Int = 0


    override fun toString(): String {
        return pokemonVariety.nameGeneral.replaceDashCapitalizeWords()
    }
}