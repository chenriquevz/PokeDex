package com.chenriquevz.pokedex.di

import android.app.Application
import com.chenriquevz.pokedex.PokeDexApplication
import com.chenriquevz.pokedex.ui.bytype.ByTypeViewModel
import com.chenriquevz.pokedex.ui.pokemon.PokemonViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AndroidInjectionModule::class, MainActivityModule::class, ViewModelAssistedFactoriesModule::class])
interface AppComponent {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    val pokemonViewModelFactory: PokemonViewModel.Factory
    val typeViewModelFactory: ByTypeViewModel.Factory

    fun inject(app: PokeDexApplication)

}


@AssistedModule
@Module(includes = [AssistedInject_ViewModelAssistedFactoriesModule::class])
abstract class ViewModelAssistedFactoriesModule