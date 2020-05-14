package com.chenriquevz.pokedex.ui.pokemon.carrossel


import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.databinding.ViewholderCarrosselBinding
import com.chenriquevz.pokedex.model.PokemonCarrossel


class CarrosselViewHolder(private val binding: ViewholderCarrosselBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(pokemon: PokemonCarrossel, imageReady: () -> Unit) {
        val context = binding.root.context

        Glide.with(context)
            .load(pokemon.urlString)
            .fitCenter()
            .placeholder(R.drawable.ic_pokemonloading)
          //  .dontAnimate()
          //  .dontTransform()
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

                    if (adapterPosition == 0) {
                        imageReady()
                    }



                    return false
                }
            })
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
