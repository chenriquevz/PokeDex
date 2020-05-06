package com.chenriquevz.pokedex.repository

import android.util.Log
import com.chenriquevz.pokedex.api.PokemonService
import com.chenriquevz.pokedex.data.PokemonDao
import com.chenriquevz.pokedex.repository.GetResult.getResult
import com.chenriquevz.pokedex.repository.GetResult.resultLiveData
import com.chenriquevz.pokedex.utils.mapToDB
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
        databaseQuery = { dao.getListByNumber() },
        incrementPage = { searchByNumber() }
    )

}
