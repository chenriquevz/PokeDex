package com.chenriquevz.pokedex.ui.pokemon

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.chenriquevz.pokedex.repository.PokemonRepository
import com.chenriquevz.pokedex.utils.urlSpeciestoString
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class PokemonViewModel @AssistedInject constructor(
    private val repository: PokemonRepository,
    @Assisted private val id: Int
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(id: Int): PokemonViewModel
    }

    val pokemon = repository.pokemonGeneral(id.toString())

    val species = Transformations.switchMap(pokemon) {
        repository.pokemonSpecies(it.data?.pokemonGeneral?.species?.urlGeneral!!.urlSpeciestoString())
    }

    val evolutionChain = Transformations.switchMap(species) {
        repository.pokemonEvolution(it.data?.pokemonSpecies?.name!!)
    }

}