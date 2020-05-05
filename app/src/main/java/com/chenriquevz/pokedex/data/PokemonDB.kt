package com.chenriquevz.pokedex.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chenriquevz.pokedex.model.*

@Database(
    entities = [PokemonAbility::class, AbilityEffectEntries::class, PokemonEvolution::class, EvolutionChainFirst::class, EvolutionChainSecond::class,
        PokemonGeneral::class, AbilitiesList::class, Stats::class, Type::class,
        PokemonSpecies::class, PokemonVarieties::class,
        PokemonByType::class, PokemonByNumber::class],
    version = 2,
    exportSchema = false
)
abstract class PokemonDB : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {

        @Volatile
        private var INSTANCE: PokemonDB? = null

        fun getInstance(context: Context): PokemonDB =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        ).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            PokemonDB::class.java, "pokemon.db"
        ).fallbackToDestructiveMigration().build()
    }
}