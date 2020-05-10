package com.chenriquevz.pokedex.ui.home


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.databinding.ViewholderHomelistadapterBinding
import com.chenriquevz.pokedex.model.PokemonByNumber
import com.chenriquevz.pokedex.utils.replaceDash
import com.chenriquevz.pokedex.utils.urlPrimaryConverter
import com.chenriquevz.pokedex.utils.urlSpritesConverter

class HomeViewHolder(private val binding: ViewholderHomelistadapterBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("DefaultLocale")
    fun bind(result: PokemonByNumber?) {
        val context = binding.root.context
        if (result != null) {
            binding.homePokemonID.text =
                context.getString(R.string.pokemonid_display, result.id.toString())
            binding.homePokemonName.text = result.name.replaceDash().capitalize()


            Glide.with(context)
                .load(result.id.urlPrimaryConverter())
                .fitCenter()
                .placeholder(R.drawable.ic_pokemonloading)
                .error(
                    Glide.with(context)
                        .load(result.id.urlSpritesConverter())
                        .placeholder(R.drawable.ic_pokemonloading)
                        .error(R.drawable.ic_pokemonloading)
                )
                .into(binding.homePokemonImage)

            binding.homeCard.setOnClickListener {

                val extras = FragmentNavigatorExtras(
                    binding.homePokemonImage to "pokemonImage",
                    binding.homePokemonName to "pokemonName")

                Navigation.findNavController(it)
                    .navigate(HomeFragmentDirections.homeToPokemon(result.id.toString()), extras)

            }
        }


    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int): HomeViewHolder {
            val binding = ViewholderHomelistadapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return HomeViewHolder(binding)
        }
    }
}