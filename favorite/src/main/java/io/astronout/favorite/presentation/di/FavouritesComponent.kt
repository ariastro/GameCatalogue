package io.astronout.favorite.presentation.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import io.astronout.core.di.FavouritesModuleDependencies
import io.astronout.favorite.presentation.FavouritesFragment

@Component(dependencies = [FavouritesModuleDependencies::class])
interface FavouritesComponent {

    fun inject(fragment: FavouritesFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favouritesModuleDependencies: FavouritesModuleDependencies): Builder
        fun build(): FavouritesComponent
    }

}