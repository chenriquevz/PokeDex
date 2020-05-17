package com.chenriquevz.pokedex.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chenriquevz.pokedex.data.relations.PokemonSpeciesRelation
import com.chenriquevz.pokedex.model.*
import com.chenriquevz.pokedex.utils.getDistinct


interface SpeciesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonSpecies(pokemon: PokemonSpecies)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonSpeciesVarieties(pokemon: List<PokemonVarieties>)
}

@Dao
abstract class PokemonSpeciesDao: SpeciesDao {

    @Transaction
    @Query("SELECT * FROM PokemonSpecies WHERE id = :id")
    protected abstract fun getPokemonSpecies(id: Int?): LiveData<PokemonSpeciesRelation?>

    fun getPokemonSpeciesDistinct(id: Int?) = getPokemonSpecies(id).getDistinct()


}