package com.chenriquevz.pokedex.di

import com.chenriquevz.pokedex.ui.bytype.ByTypeFragment
import com.chenriquevz.pokedex.ui.home.HomeFragment
import com.chenriquevz.pokedex.ui.pokemon.PokemonFragment
import com.chenriquevz.pokedex.ui.pokemon.dialogability.AbilityDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributePokemonFragment(): PokemonFragment

    @ContributesAndroidInjector
    abstract fun contributeByTypeFragment(): ByTypeFragment

    @ContributesAndroidInjector
    abstract fun contributeDialogAbilityFragment(): AbilityDialogFragment

}