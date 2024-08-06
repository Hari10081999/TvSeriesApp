package com.app.series.series_details.presentation.details

import com.app.series.main.domain.models.Genre
import com.app.series.main.domain.models.Series


data class SeriesDetailsScreenState(

    val isLoading: Boolean = false,

    val series: Series? = null,

    val videoId: String = "",
    val readableTime: String = "",

    val moviesGenresList: List<Genre> = emptyList(),
    val tvGenresList: List<Genre> = emptyList()

)