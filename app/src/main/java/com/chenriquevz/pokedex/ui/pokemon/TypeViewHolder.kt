package com.chenriquevz.pokedex.ui.pokemon


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

import com.chenriquevz.pokedex.databinding.ViewholderTypeBinding
import com.chenriquevz.pokedex.model.Type
import com.chenriquevz.pokedex.utils.urlTypetoID

class TypeViewHolder(private val binding: ViewholderTypeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("DefaultLocale")
    fun bind(result: Type?) {
        val context = binding.root.context
        if (result != null) {
            binding.pokemonType.text = result.type.nameGeneral.capitalize()

        binding.pokemonType.setOnClickListener {
            Navigation.findNavController(it).navigate(PokemonFragmentDirections.pokemonToType(result.type.urlGeneral.urlTypetoID(), result.type.nameGeneral.capitalize()))
        }

        }


    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int): TypeViewHolder {
            val binding = ViewholderTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TypeViewHolder(binding)
        }
    }
}