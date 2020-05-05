package com.chenriquevz.pokedex.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chenriquevz.pokedex.R
import com.chenriquevz.pokedex.di.Injectable
import javax.inject.Inject

class HomeFragment: Fragment(), Injectable {

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

        return rootView
    }

}