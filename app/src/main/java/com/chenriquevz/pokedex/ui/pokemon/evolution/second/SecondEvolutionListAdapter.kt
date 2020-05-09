package com.chenriquevz.pokedex.ui.pokemon.evolution.second

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.data.relations.PokemonFirstToSecondChain
import com.chenriquevz.pokedex.data.relations.PokemonGeneralRelation
import com.chenriquevz.pokedex.model.*

class SecondEvolutionListAdapter : ListAdapter<EvolutionChainSecond, RecyclerView.ViewHolder>(REPO_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SecondEvolutionViewHolder.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)


        if (repoItem != null) {
            (holder as SecondEvolutionViewHolder).bind(repoItem)
        }
    }

    override fun getItemViewType(position: Int) = R.layout.viewholder_homelistadapter

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<EvolutionChainSecond>() {
            override fun areItemsTheSame(oldItem: EvolutionChainSecond, newItem: EvolutionChainSecond): Boolean =
                oldItem.localID == newItem.localID

            override fun areContentsTheSame(oldItem: EvolutionChainSecond, newItem: EvolutionChainSecond): Boolean =
                oldItem == newItem
        }
    }


}