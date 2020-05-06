package com.chenriquevz.pokedex.ui.bytype

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.model.PokemonByNumber
import com.chenriquevz.pokedex.model.PokemonByType

class ByTypeListAdapter : ListAdapter<PokemonByType, RecyclerView.ViewHolder>(REPO_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ByTypeViewHolder.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)


        if (repoItem != null) {
            (holder as ByTypeViewHolder).bind(repoItem)
        }
    }

    override fun getItemViewType(position: Int) = R.layout.viewholder_homelistadapter

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<PokemonByType>() {
            override fun areItemsTheSame(oldItem: PokemonByType, newItem: PokemonByType): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PokemonByType, newItem: PokemonByType): Boolean =
                oldItem == newItem
        }
    }


}