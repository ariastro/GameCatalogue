package io.astronout.core.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.astronout.core.domain.usecase.GameUsecase

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavouritesModuleDependencies {

    fun usecase(): GameUsecase

}