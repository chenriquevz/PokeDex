package com.chenriquevz.pokedex.ui.pokemon.evolution.second



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.databinding.ViewholderSecondevolutionBinding
import com.chenriquevz.pokedex.model.EvolutionChainSecond
import com.chenriquevz.pokedex.ui.pokemon.PokemonFragmentDirections
import com.chenriquevz.pokedex.utils.idToImageRequest
import com.chenriquevz.pokedex.utils.replaceDashCapitalizeWords
import com.chenriquevz.pokedex.utils.urlSpeciestoInt

class SecondEvolutionViewHolder(private val binding: ViewholderSecondevolutionBinding) :
    RecyclerView.ViewHolder(binding.root) {



    fun bind(result: EvolutionChainSecond?) {
        val context = binding.root.context
        if (result != null) {


            binding.pokemonEvolutionThirdName.visibility = View.VISIBLE
            binding.pokemonEvolutionThird.visibility = View.VISIBLE

            binding.pokemonEvolutionThirdName.text = result.species.nameGeneral.replaceDashCapitalizeWords()
            Glide.with(context)
                .load(
                    result.species.urlGeneral.urlSpeciestoInt()
                        .idToImageRequest()
                )
                .fitCenter()
                .placeholder(R.drawable.ic_pokemonloading)
                .into(binding.pokemonEvolutionThird)
        }



        binding.pokemonEvolutionThird.setOnClickListener {

            Navigation.findNavController(it).navigate(
                PokemonFragmentDirections.navigationPokemon(
                    result?.species?.urlGeneral!!.urlSpeciestoInt().toString()
                )
            )

        }


    }


    companion object {
        fun create(parent: ViewGroup, viewType: Int): SecondEvolutionViewHolder {
            val binding = ViewholderSecondevolutionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return SecondEvolutionViewHolder(binding)
        }
    }
}