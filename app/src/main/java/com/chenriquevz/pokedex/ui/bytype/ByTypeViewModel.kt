package com.chenriquevz.pokedex.ui.bytype

import androidx.lifecycle.ViewModel
import com.chenriquevz.pokedex.repository.PokemonRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class ByTypeViewModel @AssistedInject constructor(
    repository: PokemonRepository,
    @Assisted private val type: Int
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(type: Int): ByTypeViewModel
    }

    val listByType = repository.listByType(type)

}