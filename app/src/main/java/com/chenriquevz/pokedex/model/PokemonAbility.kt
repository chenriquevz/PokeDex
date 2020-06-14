package com.chenriquevz.pokedex.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
    primaryKeys = ["id"],
    indices = [
        Index("id", unique = true)]
)
data class PokemonAbility (
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String
) {
    @Ignore
    @field:SerializedName("effect_entries")
    var effectEntries: List<AbilityEffectEntries>? = emptyList()
}

@Entity(
    foreignKeys = [ForeignKey(
        entity = PokemonAbility::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.CASCADE
    )], indices = [
        Index("id", unique = false)]
)
data class AbilityEffectEntries(
    val id: Int,
    @field:SerializedName("effect") val effect: String?,
    @Embedded @field:SerializedName("language") val language: GeneralEntry?,
    @field:SerializedName("short_effect") val shortEffect: String?
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "localID")
    var localID: Int = 0
}
