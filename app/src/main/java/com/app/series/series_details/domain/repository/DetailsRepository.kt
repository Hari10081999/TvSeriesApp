package com.app.series.series_details.domain.repository

import com.app.series.main.domain.models.Series
import com.app.series.util.Resource
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    suspend fun getDetails(
        type: String,
        isRefresh: Boolean,
        id: Int,
        apiKey: String
    ): Flow<Resource<Series>>

}










