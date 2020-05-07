package com.chenriquevz.pokedex.repository

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import com.chenriquevz.pokedex.api.PokemonService
import com.chenriquevz.pokedex.data.PokemonDao
import com.chenriquevz.pokedex.data.relations.PokemonGeneralRelation
import com.chenriquevz.pokedex.model.*
import com.chenriquevz.pokedex.repository.GetResult.getResult
import com.chenriquevz.pokedex.repository.GetResult.loadLiveData
import com.chenriquevz.pokedex.repository.GetResult.resultGeneralLiveData
import com.chenriquevz.pokedex.repository.GetResult.resultGeneralVarieties
import com.chenriquevz.pokedex.repository.GetResult.resultLiveData
import com.chenriquevz.pokedex.repository.GetResult.speciesLiveData
import com.chenriquevz.pokedex.utils.*
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val dao: PokemonDao,
    private val pokemonApi: PokemonService
) {

    private var lastRequestedPage = 0


    fun getAbility(ability: Int) = dao.getPokemonAbilities(ability)
    fun getPokemon(id: String) = dao.getGeneral(id)
    fun getEvolution(id: Int) = dao.getPokemonEvolutions(id)
    fun getSpecies(id: Int) = dao.getPokemonSpecies(id)

    private fun searchByNumber() {
        Log.d("homefrag", lastRequestedPage.toString())
        lastRequestedPage = 20
        Log.d("homefrag", lastRequestedPage.toString())
    }

    fun listByNumber() = resultLiveData(
        networkCall = {
            getResult {
                pokemonApi.pokemonListByNumber(
                    lastRequestedPage.toString(),
                    20
                )
            }
        },
        saveCallResult = { dao.insertListByNumber(it.results.mapToDB()) },
        databaseQuery = { dao.getListByNumber() }
    )


    fun pokemonGeneral(id: String): LiveData<Result<PokemonGeneralRelation>> {

        var databaseQuery = dao.getGeneral(id)
        if (id.isDigitsOnly()) {
            databaseQuery = dao.getGeneral(id.toInt())
        }

        return resultGeneralLiveData(
            networkCall = { getResult { pokemonApi.searchNameOrNumber(id) } },
            saveCallResult = { pokemonGeneralInsert(it) },
            databaseQuery = { databaseQuery },
            recursiveAbility = { abilityBulk(it.abilities.map { entry -> entry.ability.urlGeneral.urlAbilitytoInt() }) },
            recursiveSpecies = { speciesBulk(id, it.species.urlGeneral.urlSpeciestoString().toString()) }
        )
    }

    private suspend fun pokemonGeneralInsert(pokemon: PokemonGeneral) {

        dao.insertGeneralID(pokemon)
        dao.insertGeneralAbilities(pokemon.abilities.map { list ->
            AbilitiesList(pokemon.id, list.ability)
        })
        dao.insertGeneralType(pokemon.type.map { list ->
            Type(pokemon.id, list.type)
        })
        dao.insertGeneralStats(pokemon.stats.map { list ->
            Stats(pokemon.id, list.baseStat, list.effort, list.stat)
        })
    }

    private suspend fun pokemonSpeciesInsert(species: PokemonSpecies) {

        dao.insertPokemonSpecies(species)
        dao.insertPokemonSpeciesVarieties(species.varieties.map {
            PokemonVarieties(
                species.id,
                it.isDefault,
                it.pokemonVariety
            )
        })

    }

    private suspend fun pokemonEvolution(chainID: String) = loadLiveData(
        networkCall = { getResult { pokemonApi.pokemonEvolution(chainID) } },
        saveCallResult = { pokemonEvolutionInsert(it) }
    )

    private suspend fun pokemonEvolutionInsert(evolution: PaginationEvolution) {

        dao.insertPokemonEvolutions(PokemonEvolution(evolution.id, evolution.chain.species))

        if (evolution.chain.evolvesTo != null) {
            val firstChain: MutableList<EvolutionChainFirst> = mutableListOf()
            val secondChain: MutableList<EvolutionChainSecond> = mutableListOf()
            evolution.chain.evolvesTo.forEach { first ->

                val localID = evolution.id * 100 + evolution.chain.evolvesTo.indexOf(first).toLong()

                firstChain.add(
                    EvolutionChainFirst(
                        evolution.id,
                        localID,
                        first.species
                    )
                )

                first.evolvesTo?.forEach { second ->
                    secondChain.add(
                        EvolutionChainSecond(
                            localID,
                            second.species
                        )
                    )
                }

            }

            dao.insertPokemonEvolutionsFirst(
                firstChain
            )

            dao.insertPokemonEvolutionsSecond(
                secondChain
            )

        }

    }

    fun listByType(type: Int) = resultLiveData(
        networkCall = {
            getResult {
                pokemonApi.pokemonListByType(
                    type.toString()
                )
            }
        },
        saveCallResult = {
            dao.insertListByType(it.results.map { pokemon ->
                PokemonByType(
                    pokemon.general.urlGeneral.urlPokemonToID(),
                    pokemon.general.nameGeneral,
                    pokemon.general.urlGeneral,
                    it.id,
                    it.name
                )
            })

        },
        databaseQuery = { dao.getListByType(type) }
    )

    private suspend fun speciesBulk(pokemonID: String, speciesID: String) = speciesLiveData(
        networkCall = { getResult { pokemonApi.pokemonSpecies(speciesID) } },
        saveCallResult = { pokemonSpeciesInsert(it) },
        recursiveEvolution = { pokemonEvolution(it.evolutionChain.url.urlEvolutiontoString()) },
        recursiveVarieties = {
            varietiesBulk(
                pokemonID,
                it.varieties.map { pokemon -> pokemon.pokemonVariety.urlGeneral.urlPokemonToID() })
        }
    )


    private suspend fun varietiesBulk(pokemonID: String, speciesID: List<Int>) {

        for (pokemon in speciesID) {
            if (pokemon != pokemonID.toInt()) {
                resultGeneralVarieties(
                    networkCall = {
                        getResult {
                            pokemonApi.searchNameOrNumber(pokemon.toString())
                        }
                    },
                    saveCallResult = {
                        pokemonGeneralInsert(it)
                    },
                    recursiveAbility = {
                        abilityBulk(it.abilities.map { entry ->
                            entry.ability.urlGeneral.urlAbilitytoInt()
                        }
                        )
                    }
                )
            }

        }
    }

    private suspend fun abilityBulk(ability: List<Int>) {
        for (entry in ability) {
            loadLiveData(
                networkCall = {
                    getResult {
                        pokemonApi.pokemonAbility(
                            entry.toString()
                        )
                    }
                },
                saveCallResult = {
                    abilityInsert(it)
                }
            )
        }


    }




    private suspend fun abilityInsert(ability: PokemonAbility) {

        dao.insertPokemonAbility(ability)
        dao.insertPokemonAbilityEffect(
            AbilityEffectEntries(
                ability.id,
                ability.effectEntries?.firstOrNull()?.effect,
                ability.effectEntries?.firstOrNull()?.shortEffect
            )
        )
    }
}
