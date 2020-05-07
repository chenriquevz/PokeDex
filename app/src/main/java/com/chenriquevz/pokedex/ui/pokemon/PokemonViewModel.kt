package com.chenriquevz.pokedex.ui.pokemon

import android.util.Log
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chenriquevz.pokedex.repository.PokemonRepository
import com.chenriquevz.pokedex.repository.Result
import com.chenriquevz.pokedex.utils.urlAbilitytoInt
import com.chenriquevz.pokedex.utils.urlSpeciestoString
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PokemonViewModel @AssistedInject constructor(
    private val repository: PokemonRepository,
    @Assisted private val id: String
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(id: String): PokemonViewModel
    }


    val pokemon = repository.pokemonGeneral(id)

    fun pokemonVarieties(variety: String) = repository.getPokemon(variety)
    fun getSpecies(id: Int) = repository.getSpecies(id)

    val species = Transformations.switchMap(pokemon) {
        repository.getSpecies(it.data?.pokemonGeneral?.species?.urlGeneral!!.urlSpeciestoString())
    }

    val evolutionChain = Transformations.switchMap(species) {
        repository.getEvolution(it.pokemonSpecies?.name!!.toInt())
    }
}