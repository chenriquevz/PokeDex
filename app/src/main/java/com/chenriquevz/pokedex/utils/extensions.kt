package com.chenriquevz.pokedex.utils

import android.content.Context
import android.widget.Toast
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.model.GeneralEntry
import com.chenriquevz.pokedex.model.PokemonByNumber

fun List<GeneralEntry>.mapToDB() = this.map { entry ->
    PokemonByNumber(
        entry.urlGeneral.urlPokemonToID(),
        entry.nameGeneral,
        entry.urlGeneral
    )
}

fun String.urlPokemonToID() =
    this.removePrefix("https://pokeapi.co/api/v2/pokemon/").removeSuffix("/").toInt()

fun String.urlTypetoID() =
    this.removePrefix("https://pokeapi.co/api/v2/type/").removeSuffix("/").toInt()

fun String.urlSpeciestoString() =
    this.removePrefix("https://pokeapi.co/api/v2/pokemon-species/").removeSuffix("/").toInt()

fun String.urlAbilitytoInt() =
    this.removePrefix("https://pokeapi.co/api/v2/ability/").removeSuffix("/").toInt()

fun String.urlEvolutiontoString() =
    this.removePrefix("https://pokeapi.co/api/v2/evolution-chain/").removeSuffix("/")

fun Int.urlPrimaryConverter(): String {

    val numberToString = this.toString()
    val format = when (numberToString.length) {
        1 -> "00$numberToString"
        2 -> "0$numberToString"
        else -> numberToString
    }
    return "https://assets.pokemon.com/assets/cms2/img/pokedex/full/$format.png"
}

fun Int.urlVarietyConverter(variety: Int): String {

    val numberToString = this.toString()
    val format = when (numberToString.length) {
        1 -> "00$numberToString"
        2 -> "0$numberToString"
        else -> numberToString
    }
    return "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${format}_f$variety.png"
}

fun Int.urlSpritesConverter(): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$this.png"

fun String.replaceDash () = this.replace("-", " ")
fun Int.insertDash () = this.toString().replace(" ", "-")

fun Context.toast(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

fun Context.toastLong(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_LONG
    ).show()
}
