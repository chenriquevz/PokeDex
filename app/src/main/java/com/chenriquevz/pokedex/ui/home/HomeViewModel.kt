package com.chenriquevz.pokedex.ui.home

import androidx.lifecycle.ViewModel
import com.chenriquevz.pokedex.repository.PokemonRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: PokemonRepository) : ViewModel() {

    val listByNumber = repository.listByNumber

    fun loadMoreByNumber() {
        repository.searchByNumber()
    }

}