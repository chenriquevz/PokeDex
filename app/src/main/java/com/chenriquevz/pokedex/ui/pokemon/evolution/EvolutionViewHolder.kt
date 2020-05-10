package com.chenriquevz.pokedex.ui.pokemon.evolution


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.data.relations.PokemonFirstToSecondChain
import com.chenriquevz.pokedex.databinding.ViewholderEvolutionBinding

import com.chenriquevz.pokedex.ui.pokemon.PokemonFragmentDirections
import com.chenriquevz.pokedex.ui.pokemon.evolution.second.SecondEvolutionListAdapter
import com.chenriquevz.pokedex.utils.idToImageRequest
import com.chenriquevz.pokedex.utils.urlSpeciestoInt
import com.chenriquevz.pokedex.utils.urlSpeciestoString

class EvolutionViewHolder(private val binding: ViewholderEvolutionBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("DefaultLocale")
    fun bind(result: PokemonFirstToSecondChain?) {
        val context = binding.root.context
        if (result != null) {

            binding.pokemonEvolutionSecondName.text =
                result.pokemonFirst?.species?.nameGeneral?.capitalize()
            Glide.with(context)
                .load(
                    result.pokemonFirst?.species?.urlGeneral?.urlSpeciestoInt()
                        ?.idToImageRequest()
                )
                .fitCenter()
                .placeholder(R.drawable.ic_pokemonloading)
                .into(binding.pokemonEvolutionSecond)

            binding.pokemonEvolutionSecond.setOnClickListener {
                Navigation.findNavController(it).navigate(PokemonFragmentDirections.navigationPokemon(result.pokemonFirst?.species?.urlGeneral!!.urlSpeciestoString()))
            }

            val evolutionSize = result.pokemonSecond.size
            if (evolutionSize > 0) {
                binding.pokemonEvolutionArrow.visibility = View.VISIBLE
                val recyclerViewEvolution = binding.pokemonEvolutionRecycler
                val layoutType =
                    GridLayoutManager(
                        context,
                        evolutionSize,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                val evolutionListAdapter = SecondEvolutionListAdapter()
                recyclerViewEvolution.adapter = evolutionListAdapter
                recyclerViewEvolution.layoutManager = layoutType

                evolutionListAdapter.submitList(result.pokemonSecond)
            }


        }


    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int): EvolutionViewHolder {
            val binding = ViewholderEvolutionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return EvolutionViewHolder(binding)
        }
    }
}