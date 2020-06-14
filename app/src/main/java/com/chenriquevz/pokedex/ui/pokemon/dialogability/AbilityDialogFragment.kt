package com.chenriquevz.pokedex.ui.pokemon.dialogability

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.chenriquevz.pokedex.databinding.FragmentDialogabilitiesBinding
import com.chenriquevz.pokedex.di.Injectable
import com.chenriquevz.pokedex.di.injector
import com.chenriquevz.pokedex.di.viewModel
import java.lang.Exception

class AbilityDialogFragment : DialogFragment(), Injectable {

    private val dialogAbilityViewModel by viewModel(this) {
        injector.dialogAbilityViewModelFactory.create(args.ability)
    }
    private val args: AbilityDialogFragmentArgs by navArgs()

    private lateinit var _context: Context
    private var _binding: FragmentDialogabilitiesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogabilitiesBinding.inflate(inflater, container, false)

        val rootView = _binding?.root
        _context = rootView!!.context
        setData()

        return rootView

    }

    private fun setData() {
        dialogAbilityViewModel.ability().observe(viewLifecycleOwner, Observer { result ->

                    _binding?.abilityDescription?.text =
                        result.abilityEffectEntries?.firstOrNull()?.shortEffect



        })
    }
}