package com.chenriquevz.pokedex.ui.pokemon.evolution

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.data.relations.PokemonFirstToSecondChain
import com.chenriquevz.pokedex.data.relations.PokemonGeneralRelation
import com.chenriquevz.pokedex.model.EvolutionChainFirst
import com.chenriquevz.pokedex.model.PokemonByNumber
import com.chenriquevz.pokedex.model.PokemonByType
import com.chenriquevz.pokedex.model.Type

class EvolutionListAdapter : ListAdapter<PokemonFirstToSecondChain, RecyclerView.ViewHolder>(REPO_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EvolutionViewHolder.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)


        if (repoItem != null) {
            (holder as EvolutionViewHolder).bind(repoItem)
        }
    }

    override fun getItemViewType(position: Int) = R.layout.viewholder_homelistadapter

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<PokemonFirstToSecondChain>() {
            override fun areItemsTheSame(oldItem: PokemonFirstToSecondChain, newItem: PokemonFirstToSecondChain): Boolean =
                oldItem.pokemonFirst?.localID == newItem.pokemonFirst?.localID

            override fun areContentsTheSame(oldItem: PokemonFirstToSecondChain, newItem: PokemonFirstToSecondChain): Boolean =
                oldItem.pokemonFirst == newItem.pokemonFirst
        }
    }


}