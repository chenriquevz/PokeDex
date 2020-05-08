package com.chenriquevz.pokedex.ui.pokemon

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.data.relations.PokemonGeneralRelation
import com.chenriquevz.pokedex.data.relations.PokemonSpeciesRelation
import com.chenriquevz.pokedex.databinding.FragmentPokemonBinding
import com.chenriquevz.pokedex.di.Injectable
import com.chenriquevz.pokedex.di.injector
import com.chenriquevz.pokedex.di.viewModel
import com.chenriquevz.pokedex.model.PokemonSpecies
import com.chenriquevz.pokedex.model.PokemonVarieties
import com.chenriquevz.pokedex.repository.Result
import com.chenriquevz.pokedex.ui.pokemon.carrossel.CarrosselAdapter
import com.chenriquevz.pokedex.utils.*

@SuppressLint("DefaultLocale")
class PokemonFragment : Fragment(), AdapterView.OnItemSelectedListener, Injectable {

    private val pokemonViewModel by viewModel(this) {
        injector.pokemonViewModelFactory.create(args.pokemonID)
    }
    private val args: PokemonFragmentArgs by navArgs()

    private lateinit var viewPager: ViewPager2
    private lateinit var _context: Context
    private lateinit var spinner: AppCompatSpinner
    private lateinit var progressBar: ProgressBar
    private var _binding: FragmentPokemonBinding? = null
    private val _typeListAdapter = TypeListAdapter()
    private val _abilitiesListAdapter = AbilityListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonBinding.inflate(inflater, container, false)

        val rootView = _binding?.root
        _context = rootView!!.context

        spinner = _binding?.pokemonSpinner!!
        spinner.onItemSelectedListener = this
        progressBar = _binding?.pokemonProgressbar!!

        setData()

        return rootView
    }

    @SuppressLint("DefaultLocale")
    private fun setData() {

        val recyclerViewType = _binding?.pokemonPokemonTypes

        val layoutType =
            GridLayoutManager(
                _context,
                1,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        recyclerViewType?.adapter = _typeListAdapter
        recyclerViewType?.layoutManager = layoutType

        val recyclerViewAbility = _binding?.pokemonAbilities
        val layoutAbility =
            GridLayoutManager(
                _context,
                1,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        recyclerViewAbility?.adapter = _abilitiesListAdapter
        recyclerViewAbility?.layoutManager = layoutAbility

        viewPager = _binding!!.pokemonPokemonImage
        viewPager.offscreenPageLimit = 2

        pokemonViewModel.pokemon.observe(viewLifecycleOwner, Observer { result ->

            when (result.status) {
                Result.Status.SUCCESS -> {
                    val data = result.data
                    if (data != null) {
                        populateBasic(result.data)
                        populateImages(result.data)

                        pokemonViewModel.species
                            .observe(viewLifecycleOwner, Observer { species ->

                                if (species != null) {
                                    progressBar.visibility = View.GONE
                                    _binding?.pokemonConstraint?.visibility = View.VISIBLE

                                    populateSpecies(result.data, species)

                                }

                            })

                    }

                }
                Result.Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    _binding?.pokemonConstraint?.visibility = View.INVISIBLE
                }
                Result.Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    _context.toastLong(result.message!!)
                }
            }
        })


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    @SuppressLint("DefaultLocale")
    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

        val entry: PokemonVarieties = parent.selectedItem as PokemonVarieties
        Log.d("pokefrag", "$entry")

        pokemonViewModel.updateSelectedSpecies(position)

        pokemonViewModel.pokemonVarieties(entry.pokemonVariety.urlGeneral.urlPokemonToID())
            .observe(viewLifecycleOwner, Observer { result ->
                populateBasic(result)

                pokemonViewModel.species
                    .observe(viewLifecycleOwner, Observer { species ->
                        populateImages(result, species)
                    })
            })

    }


    private fun populateBasic(data: PokemonGeneralRelation) {

        _binding?.pokemonPokemonName?.text =
            data.pokemonGeneral?.name?.replaceDash()?.capitalize()
        _binding?.pokemonPokemonID?.text = getString(
            R.string.pokemonid_display,
            data.pokemonGeneral?.id.toString()
        )

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

            _typeListAdapter.submitList(data.type)
        }

        if (data.abilitiesList.isNotEmpty()) {

            _abilitiesListAdapter.submitList(data.abilitiesList)
        }

    }

    private fun populateImages(
        data: PokemonGeneralRelation,
        species: PokemonSpeciesRelation? = null
    ) {


        var primaryImage = data.pokemonGeneral?.id?.urlPrimaryConverter()
        if (species != null) {
            val pokemonIndexVariety =
                species.pokemonVarieties.single { it.pokemonVariety.nameGeneral == data.pokemonGeneral?.name }
            val index = species.pokemonVarieties.indexOf(pokemonIndexVariety)
            primaryImage =
                when {
                    index > 0 -> {
                        "${species.pokemonSpecies?.id?.urlVarietyConverter(index.plus(1))}"

                    }
                    else -> {
                        data.pokemonGeneral?.id?.urlPrimaryConverter()
                    }
                }
        }

        val sprites = listOf(
            primaryImage,
            data.pokemonGeneral?.sprites?.frontDefault,
            data.pokemonGeneral?.sprites?.backDefault,
            data.pokemonGeneral?.sprites?.frontShiny,
            data.pokemonGeneral?.sprites?.backShiny,
            data.pokemonGeneral?.sprites?.frontFemale,
            data.pokemonGeneral?.sprites?.backFemale,
            data.pokemonGeneral?.sprites?.frontShinyFemale,
            data.pokemonGeneral?.sprites?.backShinyFemale
        )

        val spritesNotNull = sprites.filterNotNull()

        val pagerAdapter = CarrosselAdapter(spritesNotNull)

        viewPager.adapter = pagerAdapter

        pokemonViewModel.viewPagerSelected
            .observe(viewLifecycleOwner, Observer { page ->
                viewPager.currentItem = page
            })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(page: Int) {
                pokemonViewModel.updatePageSelected(page)
            }


        })

    }

    private fun populateSpecies(
        data: PokemonGeneralRelation,
        species: PokemonSpeciesRelation
    ) {


        val varieties = species.pokemonVarieties

        if (varieties.size > 1) {

            val adapter = ArrayAdapter(
                _context,
                android.R.layout.simple_spinner_dropdown_item,
                varieties
            )

            val index = varieties.indexOf(varieties.single
            { it.pokemonVariety.nameGeneral == data.pokemonGeneral?.name }
            )

            pokemonViewModel.updateSelectedSpecies(index)
            spinner.adapter = adapter
            spinner.visibility = View.VISIBLE

            pokemonViewModel.selectedSpecies.observe(viewLifecycleOwner, Observer { selection ->

                spinner.setSelection(selection)

            })

        }

    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()

    }


}