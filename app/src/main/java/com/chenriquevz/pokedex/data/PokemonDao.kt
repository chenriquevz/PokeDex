package com.chenriquevz.pokedex.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.chenriquevz.pokedex.data.relations.PokemonAbilityRelation
import com.chenriquevz.pokedex.data.relations.PokemonEvolutionRelation
import com.chenriquevz.pokedex.data.relations.PokemonGeneralRelation
import com.chenriquevz.pokedex.data.relations.PokemonSpeciesRelation
import com.chenriquevz.pokedex.model.*
import com.chenriquevz.pokedex.utils.getDistinct

@Dao
interface PokemonDao {

    @Query("SELECT * FROM PokemonByNumber ORDER BY id ASC")
    fun getListByNumberFactory(): DataSource.Factory<Int, PokemonByNumber>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListByNumber(pokemonList: List<PokemonByNumber>)

    @Transaction
    @Query("SELECT * FROM PokemonByType WHERE type_id = :type")
    fun getListByType(type: Int): LiveData<List<PokemonByType>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListByType(pokemonList: List<PokemonByType>)

    @Transaction
    @Query("SELECT * FROM PokemonAbility WHERE id = :id")
    fun getPokemonAbilities(id: Int): LiveData<PokemonAbilityRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonAbility(pokemon: PokemonAbility)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonAbilityEffect(pokemon: AbilityEffectEntries)

    @Transaction
    @Query("SELECT * FROM PokemonEvolution WHERE id = :id")
    fun getPokemonEvolutions(id: Int?): LiveData<PokemonEvolutionRelation?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonEvolutions(pokemon: PokemonEvolution)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonEvolutionsFirst(pokemon: List<EvolutionChainFirst>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonEvolutionsSecond(pokemon: List<EvolutionChainSecond>)

}