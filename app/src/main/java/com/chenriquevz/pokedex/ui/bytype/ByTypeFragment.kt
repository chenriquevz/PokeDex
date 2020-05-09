package com.chenriquevz.pokedex.ui.bytype

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.chenriquevz.pokedex.databinding.FragmentBytypeBinding
import com.chenriquevz.pokedex.di.Injectable
import com.chenriquevz.pokedex.di.injector
import com.chenriquevz.pokedex.di.viewModel
import com.chenriquevz.pokedex.api.Result
import com.chenriquevz.pokedex.utils.toastLong

class ByTypeFragment : Fragment(), Injectable {

    private val typeViewModel by viewModel(this) {
        injector.typeViewModelFactory.create(args.pokemontype)
    }
    private val args: ByTypeFragmentArgs by navArgs()

    private lateinit var progressBar: ProgressBar
    private lateinit var _context: Context
    private var _binding: FragmentBytypeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBytypeBinding.inflate(inflater, container, false)
        progressBar = _binding?.typeProgressbar!!

        val rootView = _binding?.root
        _context = rootView!!.context

        setRecyclerView()

        return rootView
    }

    private fun setRecyclerView() {

        val recyclerView = _binding?.bytypeRecyclerview
        val typeListAdapter = ByTypeListAdapter()
        recyclerView?.adapter = typeListAdapter
        val layout =
            LinearLayoutManager(_context)
        recyclerView?.layoutManager = layout

        typeViewModel.listByType.observe(viewLifecycleOwner, Observer { result ->


            when (result.status) {
                Result.Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    typeListAdapter.submitList(result.data)
                }
                Result.Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Result.Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    _context.toastLong(result.message!!)
                }
            }
        })

    }

}
