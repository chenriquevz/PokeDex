package com.chenriquevz.pokedex.ui.pokemon.evolution.second


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.data.relations.PokemonFirstToSecondChain
import com.chenriquevz.pokedex.databinding.ViewholderEvolutionBinding
import com.chenriquevz.pokedex.databinding.ViewholderSecondevolutionBinding

import com.chenriquevz.pokedex.databinding.ViewholderTypeBinding
import com.chenriquevz.pokedex.model.EvolutionChainFirst
import com.chenriquevz.pokedex.model.EvolutionChainSecond
import com.chenriquevz.pokedex.model.Type
import com.chenriquevz.pokedex.ui.pokemon.PokemonFragmentDirections
import com.chenriquevz.pokedex.utils.urlPrimaryConverter
import com.chenriquevz.pokedex.utils.urlSpeciestoInt
import com.chenriquevz.pokedex.utils.urlTypetoID

class SecondEvolutionViewHolder(private val binding: ViewholderSecondevolutionBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("DefaultLocale")
    fun bind(result: EvolutionChainSecond?) {
        val context = binding.root.context
        if (result != null) {


            binding.pokemonEvolutionThirdName.visibility = View.VISIBLE
            binding.pokemonEvolutionThird.visibility = View.VISIBLE

            binding.pokemonEvolutionThirdName.text = result.species.nameGeneral.capitalize()
            Glide.with(context)
                .load(
                    result.species.urlGeneral.urlSpeciestoInt()
                        .urlPrimaryConverter()
                )
                .fitCenter()
                .placeholder(R.drawable.ic_pokemonloading)
                .into(binding.pokemonEvolutionThird)
        }



        binding.pokemonEvolutionThird.setOnClickListener {

            Navigation.findNavController(it).navigate(
                PokemonFragmentDirections.navigationPokemon(
                    result?.species?.urlGeneral!!.urlSpeciestoInt().toString()
                )
            )

        }


    }


    companion object {
        fun create(parent: ViewGroup, viewType: Int): SecondEvolutionViewHolder {
            val binding = ViewholderSecondevolutionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return SecondEvolutionViewHolder(binding)
        }
    }
}