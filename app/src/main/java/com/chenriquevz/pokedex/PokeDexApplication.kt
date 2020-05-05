package com.chenriquevz.pokedex

import android.app.Application
import com.chenriquevz.pokedex.di.AppComponent
import com.chenriquevz.pokedex.di.AppInjector
import com.chenriquevz.pokedex.di.DaggerAppComponent
import com.chenriquevz.pokedex.di.DaggerComponentProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class PokeDexApplication : Application(), DaggerComponentProvider, HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        initializeComponent()
    }

    open fun initializeComponent() {
        AppInjector.init(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override val component: AppComponent
        get() = DaggerAppComponent
            .builder()
            .application(this)
            .build()


}