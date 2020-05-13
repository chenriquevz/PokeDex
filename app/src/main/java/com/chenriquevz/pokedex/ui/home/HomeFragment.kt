package com.chenriquevz.pokedex.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.databinding.FragmentHomeBinding
import com.chenriquevz.pokedex.di.Injectable
import com.chenriquevz.pokedex.utils.isLettersOrDigits
import com.chenriquevz.pokedex.utils.toast
import com.chenriquevz.pokedex.utils.toastLong
import com.chenriquevz.pokedex.utils.waitForTransition
import javax.inject.Inject

class HomeFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var _context: Context
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    private var _binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        progressBar = _binding?.homeProgressbar!!

        waitForTransition(_binding?.homeRecyclerview!!)

        val rootView = _binding?.root
        _context = rootView!!.context

        setHasOptionsMenu(true)
        setRecyclerView()

        return rootView
    }

    private fun setRecyclerView() {
        val recyclerView = _binding?.homeRecyclerview
        val homeListAdapter = HomeListAdapter()
        recyclerView?.adapter = homeListAdapter
        val layout =
            LinearLayoutManager(_context)
        recyclerView?.layoutManager = layout

        progressBar.visibility = View.VISIBLE

        homeViewModel.pokemonData.observe(viewLifecycleOwner, Observer { result ->

            if (!result.isNullOrEmpty()) {
                progressBar.visibility = View.GONE
                homeListAdapter.submitList(result)


            }
        })


        homeViewModel.networkErrors.observe(viewLifecycleOwner, Observer { error ->

            if (error != null) {
                progressBar.visibility = View.GONE
                _context.toastLong(error)
            }

        })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val navController = findNavController()

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.queryHint = getString(R.string.search_hint)



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                if (query.isNotEmpty() && query.isLettersOrDigits()) {

                    val action = HomeFragmentDirections.homeToPokemon(query)
                    navController.navigate(action)

                    //TODO - fix of hardware enter button
                  //  searchView.clearFocus()
                } else {
                    _context.toast(getString(R.string.search_error))
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()

    }

}