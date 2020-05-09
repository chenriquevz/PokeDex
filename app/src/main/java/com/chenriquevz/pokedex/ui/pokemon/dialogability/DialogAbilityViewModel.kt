package com.chenriquevz.pokedex.ui.pokemon.dialogability

import androidx.lifecycle.ViewModel
import com.chenriquevz.pokedex.repository.PokemonRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class DialogAbilityViewModel @AssistedInject constructor(
    private val repository: PokemonRepository,
    @Assisted private val id: Int
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(id: Int): DialogAbilityViewModel
    }

    fun ability() = repository.getAbility(id)


}