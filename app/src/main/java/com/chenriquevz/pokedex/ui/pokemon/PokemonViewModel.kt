package com.chenriquevz.pokedex.ui.pokemon

import android.util.Log
import androidx.lifecycle.*
import com.chenriquevz.pokedex.data.relations.PokemonGeneralRelation
import com.chenriquevz.pokedex.model.PokemonSpeciesComplete
import com.chenriquevz.pokedex.model.Sprites
import com.chenriquevz.pokedex.repository.PokemonRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.chenriquevz.pokedex.utils.*

class PokemonViewModel @AssistedInject constructor(
    private val repository: PokemonRepository,
    @Assisted private val id: String
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(id: String): PokemonViewModel
    }

    private val _pokemonName = MutableLiveData<String>()
    private val pokemonName = _pokemonName.getDistinct()

    private val _pokemonID = MutableLiveData<Int>()
    private val pokemonID = _pokemonID.getDistinct()

    private val _pokemonSprites = MutableLiveData<Sprites?>()
    private val pokemonSprites = _pokemonSprites.getDistinct()

    private val _selectedSpecies = MutableLiveData<Int>()
    val selectedSpecies: LiveData<Int> = _selectedSpecies

    private val _viewPagerSelected = MutableLiveData<Int>()
    val viewPagerSelected: LiveData<Int> = _viewPagerSelected

    private val _pokemomChoice = MutableLiveData<String>()
    private val pokemomChoice: LiveData<String> = _pokemomChoice.getDistinct()

    init {
        _pokemomChoice.postValue(id)
    }

    val pokemon = Transformations.switchMap(pokemomChoice) {
        repository.pokemonDetail(it)
    }


    val species = Transformations.switchMap(pokemon) {
        _pokemonName.postValue(it.data?.pokemonGeneral?.name)
        _pokemonID.postValue(it.data?.pokemonGeneral?.id)
        _pokemonSprites.postValue(it.data?.pokemonGeneral?.sprites)
        repository.getSpecies(it.data?.pokemonGeneral?.species?.urlGeneral?.urlSpeciestoInt())
    }


    val speciesComplete: LiveData<PokemonSpeciesComplete?> =
        species.combineAndCompute(
            pokemonName,
            pokemonID,
            pokemonSprites
        ) { a, b, c, d ->
            PokemonSpeciesComplete(c, b, d, a)
        }.getDistinct()


    val evolutionChain = Transformations.switchMap(species) {
        repository.getEvolution(it?.pokemonSpecies?.evolutionChain?.url?.urlEvolutiontoInt())
    }.getDistinct()



    fun updateSelectedSpecies(newSelection: Int, newChoice: String) {
        if (_selectedSpecies.value != newSelection) {

            _selectedSpecies.postValue(newSelection)
            _pokemomChoice.postValue(newChoice)
        }
    }

    fun updatePageSelected(newSelection: Int) {
        if (_viewPagerSelected.value != newSelection) {

            _viewPagerSelected.postValue(newSelection)
        }

    }
}