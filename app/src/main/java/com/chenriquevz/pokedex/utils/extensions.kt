package com.chenriquevz.pokedex.utils

import com.chenriquevz.pokedex.model.GeneralEntry
import com.chenriquevz.pokedex.model.PokemonByNumber

fun List<GeneralEntry>.mapToDB () = this.map { entry -> PokemonByNumber(entry.urlGeneral.stringToID(), entry.nameGeneral, entry.urlGeneral) }

fun String.stringToID () =
    this.removePrefix("https://pokeapi.co/api/v2/pokemon/").removeSuffix("/").toInt()
