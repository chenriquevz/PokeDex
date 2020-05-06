package com.chenriquevz.pokedex.repository

import android.util.Log
import com.chenriquevz.pokedex.api.PokemonService
import com.chenriquevz.pokedex.data.PokemonDao
import com.chenriquevz.pokedex.model.*
import com.chenriquevz.pokedex.repository.GetResult.getResult
import com.chenriquevz.pokedex.repository.GetResult.resultLiveData
import com.chenriquevz.pokedex.utils.mapToDB
import com.chenriquevz.pokedex.utils.urlPokemonToID
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val dao: PokemonDao,
    private val pokemonApi: PokemonService
) {

    private var lastRequestedPage = 0

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

    fun pokemonGeneral(id: String) = resultLiveData(
        networkCall = { getResult { pokemonApi.searchNameOrNumber(id) } },
        saveCallResult = { pokemonGeneralInsert(it) },
        databaseQuery = { dao.getGeneralID(id.toInt()) }
    )

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

    fun pokemonSpecies(id: String) = resultLiveData(
        networkCall = { getResult { pokemonApi.pokemonSpecies(id) } },
        saveCallResult = { pokemonSpecies(it) },
        databaseQuery = { dao.getPokemonSpecies(id.toInt()) }
    )

    private suspend fun pokemonSpecies(species: PokemonSpecies) {

        dao.insertPokemonSpecies(species)
        dao.insertPokemonSpeciesVarieties(species.varieties.map {
            PokemonVarieties(
                species.id,
                it.isDefault,
                it.pokemonVariety
            )
        })

    }

    fun pokemonEvolution(chainID: String) = resultLiveData(
        networkCall = { getResult { pokemonApi.pokemonEvolution(chainID) } },
        saveCallResult = { pokemonEvolution(it) },
        databaseQuery = { dao.getPokemonEvolutions(chainID.toInt()) }
    )

    private suspend fun pokemonEvolution(evolution: PaginationEvolution) {

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
}
