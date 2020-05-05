package com.chenriquevz.pokedex.di

import android.app.Application
import com.chenriquevz.pokedex.data.PokemonDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun providePokemonDB(app: Application) = PokemonDB.getInstance(app)

    @Singleton
    @Provides
    fun providePokemonDao(db: PokemonDB) = db.pokemonDao()
/*
    @Singleton
    @Provides
    fun provideRetroFit() = PokemonService.create()

 */
}