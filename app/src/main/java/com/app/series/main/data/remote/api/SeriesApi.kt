package com.app.series.main.data.remote.api

import com.app.series.BuildConfig
import com.app.series.main.data.remote.dto.SeriesListDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesApi {

    @Headers("Authorization: Bearer $TOKEN")
    @GET("{type}/{category}")
    suspend fun getMoviesAndTvSeriesList(
        @Path("type") type: String,
        @Path("category") category: String,
        @Query("page") page: Int,
    ): SeriesListDto

    @GET("search/multi")
    suspend fun getSearchList(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): SeriesListDto

    companion object {
        const val BASE_URL = BuildConfig.base_url
        const val IMAGE_BASE_URL = BuildConfig.img_base_url
        const val API_KEY = BuildConfig.api_key
        const val TOKEN = BuildConfig.token
    }

}