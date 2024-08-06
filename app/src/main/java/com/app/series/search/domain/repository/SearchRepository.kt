package com.app.series.search.domain.repository

import com.app.series.util.Resource
import com.app.series.main.domain.models.Series
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchList(
        fetchFromRemote: Boolean,
        query: String,
        page: Int,
        apiKey: String
    ): Flow<Resource<List<Series>>>

}










