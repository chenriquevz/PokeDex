package com.chenriquevz.pokedex.ui.pokemon

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.model.*

class AbilityListAdapter : ListAdapter<AbilitiesList, RecyclerView.ViewHolder>(REPO_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AbilityViewHolder.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)


        if (repoItem != null) {
            (holder as AbilityViewHolder).bind(repoItem)
        }
    }

   // override fun getItemViewType(position: Int) = R.layout.viewholder_ability

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<AbilitiesList>() {
            override fun areItemsTheSame(oldItem: AbilitiesList, newItem: AbilitiesList): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: AbilitiesList, newItem: AbilitiesList): Boolean =
                oldItem == newItem
        }
    }


}