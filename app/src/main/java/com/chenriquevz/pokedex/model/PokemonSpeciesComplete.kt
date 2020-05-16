package com.chenriquevz.pokedex.model

import com.chenriquevz.pokedex.data.relations.PokemonSpeciesRelation

data class PokemonSpeciesComplete (
    val pokemonID: Int,
    val pokemonName: String,
    val pokemonSprites: Sprites,
    val pokemonSpecies: PokemonSpeciesRelation?
)