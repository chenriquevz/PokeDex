package com.chenriquevz.pokedex.ui.pokemon.carrossel

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CarrosselAdapter(private val imageReady: () -> Unit) :
    ListAdapter<String, RecyclerView.ViewHolder>(
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
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean =
                oldItem == newItem
        }
    }


}