package com.chenriquevz.pokedex.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.room.*
import com.chenriquevz.pokedex.data.relations.PokemonGeneralRelation
import com.chenriquevz.pokedex.model.AbilitiesList
import com.chenriquevz.pokedex.model.PokemonGeneral
import com.chenriquevz.pokedex.model.Stats
import com.chenriquevz.pokedex.model.Type
import com.chenriquevz.pokedex.utils.getDistinct
import com.chenriquevz.pokedex.utils.getDistinctPokemon

interface BaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeneralID(pokemon: PokemonGeneral)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeneralAbilities(pokemon: List<AbilitiesList>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeneralStats(pokemon: List<Stats>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeneralType(pokemon: List<Type>)

}

@Dao
abstract class PokemonDetailDao: BaseDao {

    @Transaction
    @Query("SELECT * FROM PokemonGeneral WHERE id = :id")
    protected abstract fun getGeneral(id: Int): LiveData<PokemonGeneralRelation?>

    @Transaction
    @Query("SELECT * FROM PokemonGeneral WHERE name = :name")
    protected abstract fun getGeneral(name: String): LiveData<PokemonGeneralRelation?>

    fun getGeneralDistinct(id: Int) = getGeneral(id).getDistinctPokemon()
    fun getGeneralDistinct(name: String) = getGeneral(name).getDistinctPokemon()
}


