package com.chenriquevz.pokedex.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.chenriquevz.pokedex.api.PokemonService
import com.chenriquevz.pokedex.data.PokemonDao
import com.chenriquevz.pokedex.data.relations.PokemonGeneralRelation
import com.chenriquevz.pokedex.model.PokemonByNumber
import com.chenriquevz.pokedex.repository.GetResult
import com.chenriquevz.pokedex.utils.mapToDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class HomeBoundaryCallBack(
    private val pokemonApi: PokemonService,
    private val dao: PokemonDao,
    private val coroutineScope: CoroutineScope
) : PagedList.BoundaryCallback<PokemonByNumber>() {

    private var isRequestInProgress = false
    private var lastRequestedPage = 0

    private val _networkErrors = MutableLiveData<String>()
    val networkErrors: LiveData<String> = _networkErrors

    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: PokemonByNumber) {
        requestAndSaveData()
    }

    private fun requestCompleted(error: String?) {
        lastRequestedPage++
        isRequestInProgress = false
        _networkErrors.postValue(error)
    }


    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true

        coroutineScope.launch {
            GetResult.resultLiveData2(
                networkCall = {
                    GetResult.getResult {
                        pokemonApi.pokemonListByNumber(
                            lastRequestedPage * 20,
                            20
                        )
                    }
                },
                saveCallResult = { dao.insertListByNumber(it.results.mapToDB()) },
                completedCall = { requestCompleted(it) }
            )
        }


    }


}