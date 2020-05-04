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
    @field:SerializedName("name") val name: String,
    @field:SerializedName("effect_entries") val effectEntries: List<AbilityEffectEntries>
)

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
    @field:SerializedName("effect") val effect: String,
    @field:SerializedName("short_effect") val shortEffect: String
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var localID: Int = 0
}
