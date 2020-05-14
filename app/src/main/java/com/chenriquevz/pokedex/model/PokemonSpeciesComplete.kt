package com.chenriquevz.pokedex.model

import androidx.lifecycle.LiveData
import com.chenriquevz.pokedex.data.relations.PokemonSpeciesRelation

class PokemonSpeciesComplete (
    val pokemonID: String?,
    val pokemonSpecies: PokemonSpeciesRelation?
)