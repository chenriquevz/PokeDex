package com.chenriquevz.pokedex.ui.home


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.databinding.ViewholderHomelistadapterBinding
import com.chenriquevz.pokedex.model.PokemonByNumber
import com.chenriquevz.pokedex.utils.replaceDashCapitalizeWords
import com.chenriquevz.pokedex.utils.idToImageRequest
import com.chenriquevz.pokedex.utils.toTransitionGroup
import com.chenriquevz.pokedex.utils.urlSpritesConverter

class HomeViewHolder(private val binding: ViewholderHomelistadapterBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(result: PokemonByNumber?) {
        val context = binding.root.context
        if (result != null) {
            binding.homePokemonID.text =
                context.getString(R.string.pokemonid_display, result.id)
            binding.homePokemonName.text = result.name.replaceDashCapitalizeWords()

            Glide.with(context)
                .load(result.id.idToImageRequest())
                .fitCenter()
                // .dontAnimate()
                // .dontTransform()
                .placeholder(R.drawable.ic_pokemonloading)
                .error(
                    Glide.with(context)
                        .load(result.id.urlSpritesConverter())
                        .placeholder(R.drawable.ic_pokemonloading)
                        .error(R.drawable.ic_pokemonloading)
                )
                .into(binding.homePokemonImage)

            binding.homePokemonImage.transitionName =
                context.getString(R.string.homePokemon_transition_image, result.id)
            binding.homePokemonName.transitionName =
                context.getString(R.string.homePokemon_transition_name, result.id)
            binding.homePokemonID.transitionName =
                context.getString(R.string.homePokemon_transition_id, result.id)


            binding.homeCard.setOnClickListener {


                val extras = FragmentNavigatorExtras(
                    binding.homePokemonImage.toTransitionGroup(),
                    binding.homePokemonName.toTransitionGroup(),
                    binding.homePokemonID.toTransitionGroup()
                )
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