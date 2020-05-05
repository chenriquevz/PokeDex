package com.chenriquevz.pokedex.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.model.PokemonByNumber

class HomeListAdapter : ListAdapter<PokemonByNumber, RecyclerView.ViewHolder>(REPO_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //holder.setIsRecyclable(false)
        val repoItem = getItem(position)


        if (repoItem != null) {
            (holder as HomeViewHolder).bind(repoItem)
        }
    }

    override fun getItemViewType(position: Int) = R.layout.viewholder_homelistadapter

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<PokemonByNumber>() {
            override fun areItemsTheSame(oldItem: PokemonByNumber, newItem: PokemonByNumber): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PokemonByNumber, newItem: PokemonByNumber): Boolean =
                oldItem == newItem
        }
    }


}