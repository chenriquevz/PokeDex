package com.chenriquevz.pokedex.ui.pokemon


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.databinding.ViewholderTypeBinding
import com.chenriquevz.pokedex.model.Type
import com.chenriquevz.pokedex.utils.urlTypetoID

class TypeViewHolder(private val binding: ViewholderTypeBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(result: Type?) {
        val context = binding.root.context
        if (result != null) {
            binding.pokemonType.text = result.type.nameGeneral.capitalize()

            setColorByType(context, result.type.nameGeneral)

            binding.pokemonType.setOnClickListener {
                Navigation.findNavController(it).navigate(
                    PokemonFragmentDirections.pokemonToType(
                        result.type.urlGeneral.urlTypetoID(),
                        result.type.nameGeneral.capitalize()
                    )
                )
            }
        }

    }

    private fun setColorByType(context: Context, type: String) {

        when (type) {
            "poison" ->
                binding.pokemonTypeConstraint.background.setTint(
                    ContextCompat.getColor(
                        context,
                        R.color.typePoison
                    )
                )
            "grass" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeGrass
                )
            )
            "normal" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeNormal
                )
            )
            "fire" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeFire
                )
            )
            "fighting" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeFighting
                )
            )
            "water" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeWater
                )
            )
            "flying" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeFlying
                )
            )
            "electric" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeEletric
                )
            )
            "ground" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeGround
                )
            )
            "psychic" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typePsychic
                )
            )
            "rock" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeRock
                )
            )
            "ice" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeIce
                )
            )
            "bug" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeBug
                )
            )
            "dragon" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeDragon
                )
            )
            "ghost" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeGhost
                )
            )
            "dark" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeDark
                )
            )
            "steel" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeSteel
                )
            )
            "fairy" -> binding.pokemonTypeConstraint.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.typeFairy
                )
            )
            else -> {
                binding.pokemonTypeConstraint.background.setTint(
                    ContextCompat.getColor(
                        context,
                        R.color.typeUnknown
                    )
                )
            }


        }

    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int): TypeViewHolder {
            val binding = ViewholderTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TypeViewHolder(binding)
        }
    }
}