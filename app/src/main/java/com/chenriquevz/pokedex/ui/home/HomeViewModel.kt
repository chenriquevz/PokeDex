package com.chenriquevz.pokedex.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chenriquevz.pokedex.repository.PokemonRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: PokemonRepository) : ViewModel() {

    val listByNumber = repository.listByNumber()

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {

        if (visibleItemCount + lastVisibleItemPosition + 5 >= totalItemCount) {
            //TODO - fix endless scroll
            repository.listByNumber()

        }
    }

}