package com.chenriquevz.pokedex.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.databinding.ViewholderHomelistadapterBinding
import com.chenriquevz.pokedex.model.PokemonByNumber

class HomeViewHolder(private val binding: ViewholderHomelistadapterBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(result: PokemonByNumber?) {
        val context = binding.root.context
        if (result != null) {
            binding.homePokemonID.text =
                context.getString(R.string.pokemonid_display, result.id.toString())
            binding.homePokemonName.text = result.name

        }

    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int): HomeViewHolder {
            val binding = ViewholderHomelistadapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return HomeViewHolder(binding)
        }
    }
}