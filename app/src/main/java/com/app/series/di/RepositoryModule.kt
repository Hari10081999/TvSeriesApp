package com.app.series.di

import com.app.series.main.data.repository.SeriesRepositoryImpl
import com.app.series.main.domain.repository.SeriesRepository
import com.app.series.search.data.repository.SearchRepositoryImpl
import com.app.series.search.domain.repository.SearchRepository
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
    abstract fun bindSeriesRepository(
        mediaRepositoryImpl: SeriesRepositoryImpl
    ): SeriesRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

}
