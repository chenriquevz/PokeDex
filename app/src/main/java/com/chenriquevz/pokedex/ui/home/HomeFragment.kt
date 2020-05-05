package com.chenriquevz.pokedex.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.di.Injectable
import com.chenriquevz.pokedex.repository.Result
import javax.inject.Inject

class HomeFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)

        homeViewModel.listByNumber.observe(viewLifecycleOwner, Observer { result ->

            Log.d("homeFrag", "$result")
            when (result.status) {
                Result.Status.SUCCESS -> {}
                    Result.Status.LOADING -> {}
                    Result.Status.ERROR -> {}
                }
            }

        )

        return rootView
    }


}