package com.ahmedtikiwa.insight.di

import com.ahmedtikiwa.insight.repository.OmdbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideOmdbRepository() : OmdbRepository {
        return OmdbRepository()
    }
}