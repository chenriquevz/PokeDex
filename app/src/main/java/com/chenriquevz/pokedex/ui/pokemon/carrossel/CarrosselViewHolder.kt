package com.chenriquevz.pokedex.ui.pokemon.carrossel

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.databinding.ViewholderCarrosselBinding
import com.chenriquevz.pokedex.databinding.ViewholderHomelistadapterBinding
import com.chenriquevz.pokedex.model.PokemonByNumber
import com.chenriquevz.pokedex.ui.home.HomeFragmentDirections
import com.chenriquevz.pokedex.ui.home.HomeViewHolder
import com.chenriquevz.pokedex.utils.replaceDash
import com.chenriquevz.pokedex.utils.urlPrimaryConverter
import com.chenriquevz.pokedex.utils.urlSpritesConverter

class CarrosselViewHolder(private val binding: ViewholderCarrosselBinding) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("DefaultLocale")
    fun bind(url: String) {

        Glide.with(binding.root.context)
            .load(url)
            .fitCenter()
            .placeholder(R.drawable.ic_pokemonloading)
            .into(binding.pokemomCarrossel)

        }

    companion object {
        fun create(parent: ViewGroup, viewType: Int): CarrosselViewHolder {
            val binding = ViewholderCarrosselBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return CarrosselViewHolder(binding)
        }
    }
}
