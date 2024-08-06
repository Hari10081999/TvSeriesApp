package com.app.series.main.domain.repository

import com.app.series.util.Resource
import com.app.series.main.domain.models.Series
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {

    suspend fun updateItem(series: Series)

    suspend fun insertItem(series: Series)

    suspend fun getItem(
        id: Int,
        type: String,
        category: String
    ): Series

    suspend fun getMoviesAndTvSeriesList(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int): Flow<Resource<List<Series>>>
}










