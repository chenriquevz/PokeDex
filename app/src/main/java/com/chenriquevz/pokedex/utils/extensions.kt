package com.chenriquevz.pokedex.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.chenriquevz.pokedex.data.relations.PokemonEvolutionRelation
import com.chenriquevz.pokedex.data.relations.PokemonGeneralRelation
import com.chenriquevz.pokedex.data.relations.PokemonSpeciesRelation
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

fun <T, A, B, C, D> LiveData<A>.combineAndCompute(
    other1: LiveData<B>,
    other2: LiveData<C>,
    other3: LiveData<D>,
    onChange: (A, B, C, D?) -> T
): MediatorLiveData<T> {

    var source0emitted = false
    var source1emitted = false
    var source2emitted = false
    var source3emitted = false

    val result = MediatorLiveData<T>()

    val mergeF = {
        val source0Value = this.value
        val source1Value = other1.value
        val source2Value = other2.value
        val source3Value = other3.value


        if (source0emitted && source1emitted && source2emitted && source3emitted) {
            result.value =
                onChange.invoke(source0Value!!, source1Value!!, source2Value!!, source3Value)
        }
    }

    result.addSource(this) {if(it != null) {source0emitted = true; mergeF.invoke()} else { source0emitted = false  } }
    result.addSource(other1) { if(it != null) {source1emitted = true; mergeF.invoke()} else { source1emitted = false  } }
    result.addSource(other2) { if(it != null) {source2emitted = true; mergeF.invoke()} else { source2emitted = false  } }
    result.addSource(other3) { if(it != null) {source3emitted = true; mergeF.invoke()}}

    return result
}

fun <T> LiveData<T>.getDistinct(): LiveData<T> {
    val distinctLiveData = MediatorLiveData<T>()
    distinctLiveData.addSource(this, object : Observer<T> {
        private var initialized = false
        private var lastObj: T? = null
        override fun onChanged(obj: T?) {

            if (!initialized) {
                initialized = true
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            } else if ((obj == null && lastObj != null) || obj != lastObj
            ) {
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            }
        }
    })
    return distinctLiveData
}

fun LiveData<PokemonSpeciesRelation?>.getDistinctSpecies(): LiveData<PokemonSpeciesRelation?> {
    val distinctLiveData = MediatorLiveData<PokemonSpeciesRelation?>()
    distinctLiveData.addSource(this, object : Observer<PokemonSpeciesRelation?> {
        private var initialized = false
        private var lastObj: PokemonSpeciesRelation? = null
        override fun onChanged(obj: PokemonSpeciesRelation?) {

            if (!initialized) {
                initialized = true
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            } else if ((obj == null && lastObj != null) || obj != lastObj) {
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            }
        }
    })
    return distinctLiveData
}



fun LiveData<PokemonEvolutionRelation?>.getDistinctEvolution(): LiveData<PokemonEvolutionRelation?> {
    val distinctLiveData = MediatorLiveData<PokemonEvolutionRelation?>()
    distinctLiveData.addSource(this, object : Observer<PokemonEvolutionRelation?> {
        private var initialized = false
        private var lastObj: PokemonEvolutionRelation? = null
        override fun onChanged(obj: PokemonEvolutionRelation?) {

             if (obj?.pokemonChainFirst.isNullOrEmpty() || obj?.pokemonChainFirst?.firstOrNull()?.pokemonSecond.isNullOrEmpty()) return

            if (!initialized) {
                initialized = true
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            } else if ((obj == null && lastObj != null) || obj != lastObj) {
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            }
        }
    })
    return distinctLiveData
}


fun LiveData<PokemonGeneralRelation?>.getDistinctPokemon(): LiveData<PokemonGeneralRelation?> {
    val distinctLiveData = MediatorLiveData<PokemonGeneralRelation?>()
    distinctLiveData.addSource(this, object : Observer<PokemonGeneralRelation?> {
        private var initialized = false
        private var lastObj: PokemonGeneralRelation? = null
        override fun onChanged(obj: PokemonGeneralRelation?) {

            if (obj?.stats.isNullOrEmpty() || obj?.abilitiesList.isNullOrEmpty() || obj?.type.isNullOrEmpty()
            ) return

            if (!initialized) {
                initialized = true
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            } else if ((obj == null && lastObj != null) || obj != lastObj) {
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            }
        }
    })
    return distinctLiveData
}

