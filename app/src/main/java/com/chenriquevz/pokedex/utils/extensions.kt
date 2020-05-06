package com.chenriquevz.pokedex.utils

import android.util.Log
import com.chenriquevz.pokedex.model.GeneralEntry
import com.chenriquevz.pokedex.model.PokemonByNumber

fun List<GeneralEntry>.mapToDB() = this.map { entry ->
    PokemonByNumber(
        entry.urlGeneral.stringToID(),
        entry.nameGeneral,
        entry.urlGeneral
    )
}

fun String.stringToID() =
    this.removePrefix("https://pokeapi.co/api/v2/pokemon/").removeSuffix("/").toInt()

fun Int.urlPrimaryConverter(): String {

    val numberToString = this.toString()
    val format = when (numberToString.length) {
        1 -> "00$numberToString"
        2 -> "0$numberToString"
        else -> numberToString
    }
    return "https://assets.pokemon.com/assets/cms2/img/pokedex/full/$format.png"
}

fun Int.urlSpritesConverter(): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$this.png"
