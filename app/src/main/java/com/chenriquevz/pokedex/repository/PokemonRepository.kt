package com.chenriquevz.pokedex.repository

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import com.chenriquevz.pokedex.api.PokemonService
import com.chenriquevz.pokedex.api.Result
import com.chenriquevz.pokedex.data.PokemonDao
import com.chenriquevz.pokedex.data.relations.PokemonGeneralRelation
import com.chenriquevz.pokedex.model.*
import com.chenriquevz.pokedex.repository.GetResult.responseIntoResult
import com.chenriquevz.pokedex.repository.GetResult.resultCallSave
import com.chenriquevz.pokedex.repository.GetResult.resultPokemonDetail
import com.chenriquevz.pokedex.repository.GetResult.varietiesCallSave
import com.chenriquevz.pokedex.repository.GetResult.resultProvideCallSaveLiveData
import com.chenriquevz.pokedex.repository.GetResult.speciesCallSave
import com.chenriquevz.pokedex.ui.home.HomeBoundaryCallBack
import com.chenriquevz.pokedex.utils.*
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val dao: PokemonDao,
    private val pokemonApi: PokemonService
) {


    fun getAbility(ability: Int) = dao.getPokemonAbilities(ability)
    fun getPokemon(id: Int) = dao.getGeneral(id)
    fun getEvolution(id: Int) = dao.getPokemonEvolutions(id)
    fun getSpecies(id: Int) = dao.getPokemonSpecies(id)


    fun listByNumber(coroutineScope: CoroutineScope): PokemonByNumberPaged {

        val dataSourceFactory = dao.getListByNumberFactory()

        val boundaryCallback = HomeBoundaryCallBack(pokemonApi, dao, coroutineScope)
        val error = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, 20)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return PokemonByNumberPaged(data, error)

    }

    fun listByType(type: Int) = resultProvideCallSaveLiveData(
        networkCall = {
            responseIntoResult {
                pokemonApi.pokemonListByType(
                    type
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


    fun pokemonDetail(id: String): LiveData<Result<PokemonGeneralRelation>> {

        var databaseQuery = dao.getGeneral(id)
        if (id.isDigitsOnly()) {
            databaseQuery = dao.getGeneral(id.toInt())
        }

        return resultPokemonDetail(
            networkCall = { responseIntoResult { pokemonApi.searchNameOrNumber(id) } },
            saveCallResult = { pokemonDetailInsert(it) },
            databaseQuery = { databaseQuery },
            recursiveAbility = { abilityLoad(it.abilities.map { entry -> entry.ability.urlGeneral.urlAbilitytoInt() }) },
            recursiveSpecies = {
                speciesLoad(
                    id,
                    it.species.urlGeneral.urlSpeciestoInt()
                )
            }
        )
    }

    private suspend fun pokemonDetailInsert(pokemon: PokemonGeneral) {

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

    private suspend fun pokemonEvolution(chainID: Int) = resultCallSave(
        networkCall = { responseIntoResult { pokemonApi.pokemonEvolution(chainID) } },
        saveCallResult = { pokemonEvolutionInsert(it) }
    )



    private suspend fun speciesLoad(pokemonID: String, speciesID: Int) = speciesCallSave(
        networkCall = { responseIntoResult { pokemonApi.pokemonSpecies(speciesID) } },
        saveCallResult = { pokemonSpeciesInsert(it) },
        recursiveEvolution = { pokemonEvolution(it.evolutionChain.url.urlEvolutiontoInt()) },
        recursiveVarieties = {
            varietiesLoad(
                pokemonID,
                it.varieties.map { pokemon -> pokemon.pokemonVariety.urlGeneral.urlPokemonToID() })
        }
    )

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

    private suspend fun varietiesLoad(pokemonID: String, speciesID: List<Int>) {

        for (pokemon in speciesID) {
            if (pokemon.toString() != pokemonID) {
                varietiesCallSave(
                    networkCall = {
                        responseIntoResult {
                            pokemonApi.searchNameOrNumber(pokemon.toString())
                        }
                    },
                    saveCallResult = {
                        pokemonDetailInsert(it)
                    },
                    recursiveAbility = {
                        abilityLoad(it.abilities.map { entry ->
                            entry.ability.urlGeneral.urlAbilitytoInt()
                        }
                        )
                    }
                )
            }

        }
    }

    private suspend fun abilityLoad(ability: List<Int>) {
        for (entry in ability) {
            resultCallSave(
                networkCall = {
                    responseIntoResult {
                        pokemonApi.pokemonAbility(
                            entry
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


    private suspend fun pokemonEvolutionInsert(evolution: PaginationEvolution) {

        dao.insertPokemonEvolutions(PokemonEvolution(evolution.id, evolution.chain.species))

        if (evolution.chain.evolvesTo != null) {

            //Existe um issue relatado em https://github.com/PokeAPI/pokeapi/issues/163 sobre a chain do wurmple não seguir a lógica
            //padrão da API. Foi feito um pull request mas aparentemente continua a mesma coisa.
            if (evolution.chain.species.nameGeneral == "wurmple") {

                val firstChain: MutableList<EvolutionChainFirst> = mutableListOf()
                val secondChain: MutableList<EvolutionChainSecond> = mutableListOf()
                firstChain.add(
                    EvolutionChainFirst(
                        evolution.id,
                        evolution.id * 100.toLong(),
                        evolution.chain.evolvesTo[0].species
                    )
                )

                secondChain.add(
                    EvolutionChainSecond(
                        evolution.id * 100.toLong(),
                        evolution.chain.evolvesTo[0].evolvesTo?.get(0)?.species!!
                    )
                )

                firstChain.add(
                    EvolutionChainFirst(
                        evolution.id,
                        evolution.id * 100 + 1.toLong(),
                        evolution.chain.evolvesTo[0].evolvesTo?.get(1)?.species!!
                    )
                )

                secondChain.add(
                    EvolutionChainSecond(
                        evolution.id * 100 + 1.toLong(),
                        evolution.chain.evolvesTo[0].evolvesTo?.get(1)?.evolvesTo?.get(0)?.species!!
                    )
                )

                dao.insertPokemonEvolutionsFirst(
                    firstChain
                )

                dao.insertPokemonEvolutionsSecond(
                    secondChain
                )


            } else {

                val firstChain: MutableList<EvolutionChainFirst> = mutableListOf()
                val secondChain: MutableList<EvolutionChainSecond> = mutableListOf()
                evolution.chain.evolvesTo.forEach { first ->

                    val localID =
                        evolution.id * 100 + evolution.chain.evolvesTo.indexOf(first).toLong()

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

    }

}
