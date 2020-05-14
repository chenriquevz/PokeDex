package com.chenriquevz.pokedex.ui.home

import androidx.lifecycle.*
import com.chenriquevz.pokedex.repository.PokemonRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(repository: PokemonRepository) : ViewModel() {

    private val pokemonList = repository.listByNumber(viewModelScope)

    val pokemonData = pokemonList.data

    val networkErrors: LiveData<String> = pokemonList.networkErrors

}

