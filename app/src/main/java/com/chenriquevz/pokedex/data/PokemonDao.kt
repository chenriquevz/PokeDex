package com.chenriquevz.pokedex.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chenriquevz.pokedex.data.relations.PokemonAbilityRelation
import com.chenriquevz.pokedex.data.relations.PokemonEvolutionRelation
import com.chenriquevz.pokedex.data.relations.PokemonGeneralRelation
import com.chenriquevz.pokedex.data.relations.PokemonSpeciesRelation
import com.chenriquevz.pokedex.model.*

@Dao
interface PokemonDao {

    @Transaction
    @Query("SELECT * FROM PokemonByNumber ORDER BY id ASC")
    fun getListByNumber(): LiveData<List<PokemonByNumber>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListByNumber(pokemonList: List<PokemonByNumber>)

    @Transaction
    @Query("SELECT * FROM PokemonByType WHERE type_id = :type")
    fun getListByType(type: Int): LiveData<List<PokemonByType>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListByType(pokemonList: List<PokemonByType>)

    @Transaction
    @Query("SELECT * FROM PokemonGeneral WHERE id = :id")
    fun getGeneral(id: Int): LiveData<PokemonGeneralRelation>

    @Transaction
    @Query("SELECT * FROM PokemonGeneral WHERE name = :name")
    fun getGeneral(name: String): LiveData<PokemonGeneralRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeneralID(pokemon: PokemonGeneral)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeneralAbilities(pokemon: List<AbilitiesList>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeneralStats(pokemon: List<Stats>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeneralType(pokemon: List<Type>)

    @Transaction
    @Query("SELECT * FROM PokemonAbility WHERE id = :id")
    fun getPokemonAbilities(id: Int): LiveData<PokemonAbilityRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonAbility(pokemon: PokemonAbility)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonAbilityEffect(pokemon: AbilityEffectEntries)

    @Transaction
    @Query("SELECT * FROM PokemonSpecies WHERE id = :id")
    fun getPokemonSpecies(id: Int): LiveData<PokemonSpeciesRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonSpecies(pokemon: PokemonSpecies)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonSpeciesVarieties(pokemon: List<PokemonVarieties>)

    @Transaction
    @Query("SELECT * FROM PokemonEvolution WHERE id = :id")
    fun getPokemonEvolutions(id: Int): LiveData<List<PokemonEvolutionRelation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonEvolutions(pokemon: PokemonEvolution)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonEvolutionsFirst(pokemon: List<EvolutionChainFirst>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonEvolutionsSecond(pokemon: List<EvolutionChainSecond>)

}