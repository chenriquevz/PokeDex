package com.chenriquevz.pokedex.ui.pokemon.carrossel

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.data.relations.PokemonFirstToSecondChain
import com.chenriquevz.pokedex.model.PokemonCarrossel
import com.chenriquevz.pokedex.ui.pokemon.evolution.EvolutionListAdapter
import com.chenriquevz.pokedex.ui.pokemon.evolution.EvolutionViewHolder

class CarrosselAdapter(private val imageReady: () -> Unit) :
    ListAdapter<PokemonCarrossel, RecyclerView.ViewHolder>(
        REPO_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int) =
        CarrosselViewHolder.create(parent, type)


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)


        if (repoItem != null) {
            (holder as CarrosselViewHolder).bind(repoItem) { imageReady() }
        }
    }


    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<PokemonCarrossel>() {
            override fun areItemsTheSame(
                oldItem: PokemonCarrossel,
                newItem: PokemonCarrossel
            ): Boolean =
                oldItem.urlString == newItem.urlString

            override fun areContentsTheSame(
                oldItem: PokemonCarrossel,
                newItem: PokemonCarrossel
            ): Boolean =
                oldItem.pokemonID == newItem.pokemonID
        }
    }


}