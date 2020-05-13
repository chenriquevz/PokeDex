package com.chenriquevz.pokedex.ui.pokemon

import androidx.lifecycle.*
import com.chenriquevz.pokedex.repository.PokemonRepository
import com.chenriquevz.pokedex.utils.urlEvolutiontoInt
import com.chenriquevz.pokedex.utils.urlSpeciestoInt
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class PokemonViewModel @AssistedInject constructor(
    private val repository: PokemonRepository,
    @Assisted private val id: String
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(id: String): PokemonViewModel
    }

    val pokemon = repository.pokemonDetail(id)
    fun pokemonVarieties(variety: Int) = repository.getPokemon(variety)

    private val _firstPokemonName = MutableLiveData<String>()
    val firstPokemonName: LiveData<String> = _firstPokemonName

    val species = Transformations.switchMap(pokemon) {
        _firstPokemonName.postValue(it.data?.pokemonGeneral?.name)
        repository.getSpecies(it.data?.pokemonGeneral?.species?.urlGeneral!!.urlSpeciestoInt())
    }

    val evolutionChain = Transformations.switchMap(species) {
        repository.getEvolution(it.pokemonSpecies?.evolutionChain?.url!!.urlEvolutiontoInt())
    }

    private val _selectedSpecies = MutableLiveData<Int>()
    val selectedSpecies: LiveData<Int> = _selectedSpecies

    fun updateSelectedSpecies(newSelection: Int) {
        if (_selectedSpecies.value == newSelection) return

        _selectedSpecies.postValue(newSelection)
    }

    private val _viewPagerSelected = MutableLiveData<Int>()
    val viewPagerSelected: LiveData<Int> = _viewPagerSelected

    fun updatePageSelected(newSelection: Int) {
        _viewPagerSelected.postValue(newSelection)
    }
}