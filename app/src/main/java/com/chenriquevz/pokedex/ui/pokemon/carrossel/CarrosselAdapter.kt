package com.chenriquevz.pokedex.ui.pokemon.carrossel

import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chenriquevz.pokedex.R

class CarrosselAdapter(private val listUrl: List<String>) : RecyclerView.Adapter<CarrosselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int) =
        CarrosselViewHolder.create(parent, type)

    override fun onBindViewHolder(holder: CarrosselViewHolder, position: Int) {
        holder.bind(listUrl.get(position))
    }

    override fun getItemViewType(position: Int) = R.layout.viewholder_carrossel
    override fun getItemCount(): Int = listUrl.size

}