package com.chenriquevz.pokedex.ui.pokemon.carrossel


import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.databinding.ViewholderCarrosselBinding
import com.chenriquevz.pokedex.model.PokemonCarrossel
import com.chenriquevz.pokedex.utils.imageRequestToId

class CarrosselViewHolder(private val binding: ViewholderCarrosselBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(pokemon: PokemonCarrossel, imageReady: () -> Unit) {
        val context = binding.root.context
       // binding.pokemomCarrossel.transitionName = context.getString(R.string.homePokemon_transition_image, pokemon.pokemonID)

        Glide.with(context)
            .load(pokemon.urlString)
            .fitCenter()
            .placeholder(R.drawable.ic_pokemonloading)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    p0: GlideException?,
                    p1: Any?,
                    p2: Target<Drawable>?,
                    p3: Boolean
                ): Boolean {

                    if (adapterPosition == 0)
                    imageReady()

                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {

                    if (adapterPosition == 0)
                    imageReady()


                    return false
                }
            })
            .dontAnimate()
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
