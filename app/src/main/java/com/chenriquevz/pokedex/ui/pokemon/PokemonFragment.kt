package com.chenriquevz.pokedex.ui.pokemon

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.data.relations.PokemonSpeciesRelation
import com.chenriquevz.pokedex.databinding.FragmentPokemonBinding
import com.chenriquevz.pokedex.di.Injectable
import com.chenriquevz.pokedex.di.injector
import com.chenriquevz.pokedex.di.viewModel
import com.chenriquevz.pokedex.repository.Result
import com.chenriquevz.pokedex.ui.pokemon.carrossel.CarrosselAdapter
import com.chenriquevz.pokedex.utils.replaceDash
import javax.inject.Inject

class PokemonFragment : Fragment(), Injectable {

    private val pokemonViewModel by viewModel(this) {
        injector.pokemonViewModelFactory.create(args.pokemonID)
    }
    private val args: PokemonFragmentArgs by navArgs()

    private lateinit var viewPager: ViewPager2
    private lateinit var _context: Context
    private var _binding: FragmentPokemonBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonBinding.inflate(inflater, container, false)

        val rootView = _binding?.root
        _context = rootView!!.context

        setData()

        return rootView
    }

    @SuppressLint("DefaultLocale")
    private fun setData() {

        val recyclerViewType = _binding?.pokemonPokemonTypes
        val typeListAdapter = TypeListAdapter()
        val layoutType =
            GridLayoutManager(_context, 1, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewType?.adapter = typeListAdapter
        recyclerViewType?.layoutManager = layoutType

        val recyclerViewAbility = _binding?.pokemonAbilities
        val abilityListAdapter = AbilityListAdapter()
        val layoutAbility =
            GridLayoutManager(_context, 1, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewAbility?.adapter = abilityListAdapter
        recyclerViewAbility?.layoutManager = layoutAbility

        val spinner = _binding?.pokemonSpinner

        pokemonViewModel.pokemon.observe(viewLifecycleOwner, Observer { result ->

            Log.d("pokeFrag", "$result")
            when (result.status) {
                Result.Status.SUCCESS -> {
                    val data = result.data
                    if (data != null) {
                        _binding?.pokemonPokemonName?.text = data.pokemonGeneral?.name?.replaceDash()?.capitalize()
                        _binding?.pokemonPokemonID?.text = getString(
                            R.string.pokemonid_display,
                            data.pokemonGeneral?.id.toString()
                        )

                        viewPager = _binding!!.pokemonPokemonImage
                        val pagerAdapter = CarrosselAdapter(this, 10)
                        viewPager.adapter = pagerAdapter

                        if (data.stats.isNotEmpty()) {
                            _binding?.pokemonPokemonSpeed?.text = getString(
                                R.string.pokemon_stat,
                                data.stats[0].stat.nameGeneral.replaceDash().capitalize(),
                                data.stats[0].baseStat
                            )
                            _binding?.pokemonPokemonSpecialDefense?.text = getString(
                                R.string.pokemon_stat,
                                data.stats[1].stat.nameGeneral.replaceDash().capitalize(),
                                data.stats[1].baseStat
                            )
                            _binding?.pokemonPokemonSpecialAttack?.text = getString(
                                R.string.pokemon_stat,
                                data.stats[2].stat.nameGeneral.replaceDash().capitalize(),
                                data.stats[2].baseStat
                            )
                            _binding?.pokemonPokemonDefense?.text = getString(
                                R.string.pokemon_stat,
                                data.stats[3].stat.nameGeneral.replaceDash().capitalize(),
                                data.stats[3].baseStat
                            )
                            _binding?.pokemonPokemonAttack?.text = getString(
                                R.string.pokemon_stat,
                                data.stats[4].stat.nameGeneral.replaceDash().capitalize(),
                                data.stats[4].baseStat
                            )
                            _binding?.pokemonPokemonHP?.text = getString(
                                R.string.pokemon_stat,
                                data.stats[5].stat.nameGeneral.replaceDash().capitalize(),
                                data.stats[5].baseStat
                            )
                        }

                        if (data.type.isNotEmpty()) {
                            typeListAdapter.submitList(data.type)
                        }

                        if (data.abilitiesList.isNotEmpty()) {
                            abilityListAdapter.submitList(data.abilitiesList)
                        }

                        pokemonViewModel.species.observe(viewLifecycleOwner, Observer { species ->

                            when (result.status) {
                                Result.Status.SUCCESS -> {

                                    if (species.data != null) {

                                        val varieties = species.data.pokemonVarieties.map {
                                            it.pokemonVariety.nameGeneral.replaceDash()
                                                .capitalize()
                                        }

                                        if (varieties.size > 1) {
                                            val adapter = ArrayAdapter(
                                                _context,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                varieties
                                            )
                                            spinner?.adapter = adapter
                                            spinner?.visibility = View.VISIBLE
                                        }
                                    }

                                }
                                else -> {
                                }
                            }
                        })


                    }
                }
                Result.Status.LOADING -> {
                }
                Result.Status.ERROR -> {
                }
            }
        })

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()

    }

}