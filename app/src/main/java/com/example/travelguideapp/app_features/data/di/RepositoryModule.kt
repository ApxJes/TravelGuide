package com.example.travelguideapp.app_features.data.di

import com.example.travelguideapp.app_features.data.repository.TourismRepositoryImpl
import com.example.travelguideapp.app_features.domain.repository.TourismRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providesRepoImpl(
        tourismRepositoryImpl: TourismRepositoryImpl
    ): TourismRepository
}