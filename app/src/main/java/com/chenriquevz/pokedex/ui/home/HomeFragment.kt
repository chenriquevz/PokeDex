package com.chenriquevz.pokedex.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.databinding.FragmentHomeBinding
import com.chenriquevz.pokedex.di.Injectable
import com.chenriquevz.pokedex.repository.Result
import com.chenriquevz.pokedex.utils.toast
import javax.inject.Inject

class HomeFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var _context: Context
    private lateinit var searchView: SearchView
    private var _binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

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


        homeViewModel.listByNumber.observe(viewLifecycleOwner, Observer { result ->

            Log.d("homeFrag", "$result")
            when (result.status) {
                Result.Status.SUCCESS -> {
                    homeListAdapter.submitList(result.data)
                }
                Result.Status.LOADING -> {
                }
                Result.Status.ERROR -> {
                }
            }
        })

        recyclerView?.addOnScrollListener(object :
            androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: androidx.recyclerview.widget.RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layout.itemCount
                val visibleItemCount = layout.childCount
                val lastVisibleItem = layout.findLastVisibleItemPosition()

                homeViewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val navController = findNavController()

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.isIconified = false
        searchView.maxWidth = Int.MAX_VALUE
        searchView.queryHint = getString(R.string.search_hint)
        searchView.clearFocus()


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                if (query.isNotEmpty()) {
                    val action = HomeFragmentDirections.homeToPokemon(query.toInt())
                    navController.navigate(action)
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