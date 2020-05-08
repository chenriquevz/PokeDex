package com.chenriquevz.pokedex.ui.pokemon

import android.util.Log
import androidx.lifecycle.*
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


    private val _selectedSpecies = MutableLiveData<Int>()
    val selectedSpecies: LiveData<Int> = _selectedSpecies

    fun updateSelectedSpecies(newSelection: Int) {
        _selectedSpecies.postValue(newSelection)
    }

    private val _viewPagerSelected = MutableLiveData<Int>()
    val viewPagerSelected: LiveData<Int> = _viewPagerSelected

    fun updatePageSelected(newSelection: Int) {
        _viewPagerSelected.postValue(newSelection)
    }

    val pokemon = repository.pokemonGeneral(id)
    fun pokemonVarieties(variety: Int) = repository.getPokemon(variety)

    val species = Transformations.switchMap(pokemon) {
        Log.d("pokefrag", "viewmodel $it")
        repository.getSpecies(it.data?.pokemonGeneral?.species?.urlGeneral!!.urlSpeciestoString())
    }

    val evolutionChain = Transformations.switchMap(species) {
        repository.getEvolution(it.pokemonSpecies?.name!!.toInt())
    }
}