package com.chenriquevz.pokedex.ui.home

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.chenriquevz.pokedex.model.PokemonByNumber
import com.chenriquevz.pokedex.model.PokemonByNumberPaged
import com.chenriquevz.pokedex.repository.PokemonRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(repository: PokemonRepository) : ViewModel() {

    private val pokemonList = repository.listByNumber(viewModelScope)

    val pokemonData = pokemonList.data

    val networkErrors: LiveData<String> = pokemonList.networkErrors


}

