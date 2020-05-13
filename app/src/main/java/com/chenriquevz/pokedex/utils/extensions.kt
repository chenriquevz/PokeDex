package com.chenriquevz.pokedex.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
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

fun String?.urlSpeciestoInt() =
    this?.removePrefix("https://pokeapi.co/api/v2/pokemon-species/")?.removeSuffix("/")?.toInt()

fun String.urlSpeciestoString() =
    this.removePrefix("https://pokeapi.co/api/v2/pokemon-species/").removeSuffix("/")

fun String.urlAbilitytoInt() =
    this.removePrefix("https://pokeapi.co/api/v2/ability/").removeSuffix("/").toInt()

fun String.urlEvolutiontoInt() =
    this.removePrefix("https://pokeapi.co/api/v2/evolution-chain/").removeSuffix("/").toInt()

fun Int.urlSpritesConverter(): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$this.png"

fun Int?.idToImageRequest(): String {

    val numberToString = this.toString()
    val format = when (numberToString.length) {
        1 -> "00$numberToString"
        2 -> "0$numberToString"
        else -> numberToString
    }
    return "https://assets.pokemon.com/assets/cms2/img/pokedex/full/$format.png"
}

fun String.imageRequestToId(): Int =
    removePrefix("https://assets.pokemon.com/assets/cms2/img/pokedex/full/")
        .substringAfterLast("/")
        .replaceAfterLast("_", "")
        .replace("_", "").toInt()

fun Int.urlVarietyConverter(variety: Int): String {

    val numberToString = this.toString()
    val format = when (numberToString.length) {
        1 -> "00$numberToString"
        2 -> "0$numberToString"
        else -> numberToString
    }
    return "https://assets.pokemon.com/assets/cms2/img/pokedex/full/${format}_f$variety.png"
}

@SuppressLint("DefaultLocale")
fun String.replaceDashCapitalizeWords() =
    replace("-", " ").split(" ").joinToString(" ") { it.capitalize() }

fun String.isLettersOrDigits(): Boolean {
    return this.filter { it in 'A'..'Z' || it in 'a'..'z' || it in '0'..'9' || it in " " }
        .length == this.length
}

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

fun View.toTransitionGroup() = this to transitionName

fun Fragment.waitForTransition(targetView: View) {
    postponeEnterTransition()
    targetView.doOnPreDraw { startPostponedEnterTransition() }
}

fun Fragment.readyForTransition(targetView: View) {
    this.startPostponedEnterTransition()
}

