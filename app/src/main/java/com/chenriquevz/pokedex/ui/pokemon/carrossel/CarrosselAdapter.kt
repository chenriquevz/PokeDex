package com.chenriquevz.pokedex.ui.pokemon.carrossel

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.model.PokemonCarrossel

class CarrosselAdapter(private val listUrl: List<PokemonCarrossel>, private val imageReady: () -> Unit) : RecyclerView.Adapter<CarrosselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int) =
        CarrosselViewHolder.create(parent, type)

    override fun onBindViewHolder(holder: CarrosselViewHolder, position: Int) {
        holder.bind(listUrl[position]) {imageReady()}
    }

    override fun getItemViewType(position: Int) = R.layout.viewholder_carrossel
    override fun getItemCount(): Int = listUrl.size

}