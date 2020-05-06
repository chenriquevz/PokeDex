package com.chenriquevz.pokedex.ui.pokemon

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.data.relations.PokemonGeneralRelation
import com.chenriquevz.pokedex.model.PokemonByNumber
import com.chenriquevz.pokedex.model.PokemonByType
import com.chenriquevz.pokedex.model.Type

class TypeListAdapter : ListAdapter<Type, RecyclerView.ViewHolder>(REPO_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TypeViewHolder.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)


        if (repoItem != null) {
            (holder as TypeViewHolder).bind(repoItem)
        }
    }

    override fun getItemViewType(position: Int) = R.layout.viewholder_homelistadapter

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Type>() {
            override fun areItemsTheSame(oldItem: Type, newItem: Type): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Type, newItem: Type): Boolean =
                oldItem == newItem
        }
    }


}