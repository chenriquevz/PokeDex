package com.chenriquevz.pokedex.ui.pokemon


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.databinding.ViewholderAbilityBinding
import com.chenriquevz.pokedex.databinding.ViewholderHomelistadapterBinding
import com.chenriquevz.pokedex.databinding.ViewholderTypeBinding
import com.chenriquevz.pokedex.model.AbilitiesList
import com.chenriquevz.pokedex.model.PokemonByNumber
import com.chenriquevz.pokedex.model.Type
import com.chenriquevz.pokedex.utils.*

class AbilityViewHolder(private val binding: ViewholderAbilityBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("DefaultLocale")
    fun bind(result: AbilitiesList?) {
        val context = binding.root.context
        if (result != null) {
            binding.pokemonAbility.text = result.ability.nameGeneral.replaceDash().capitalize()

            binding.pokemonAbility.setOnClickListener {
                //abilitiesDescription(result.ability.urlGeneral.urlAbilitytoInt())
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