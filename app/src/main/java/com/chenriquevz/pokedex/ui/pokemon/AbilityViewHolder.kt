package com.chenriquevz.pokedex.ui.pokemon


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

import com.chenriquevz.pokedex.databinding.ViewholderAbilityBinding
import com.chenriquevz.pokedex.model.AbilitiesList
import com.chenriquevz.pokedex.utils.*

class AbilityViewHolder(private val binding: ViewholderAbilityBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("DefaultLocale")
    fun bind(result: AbilitiesList?) {

        if (result != null) {
            binding.pokemonAbility.text = result.ability.nameGeneral.replaceDashCapitalizeWords()

            binding.pokemonAbility.setOnClickListener {

                    Navigation.findNavController(it).navigate(PokemonFragmentDirections.pokemonToAbility(result.ability.urlGeneral.urlAbilitytoInt()))

            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int): AbilityViewHolder {
            val binding = ViewholderAbilityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return AbilityViewHolder(binding)
        }
    }
}