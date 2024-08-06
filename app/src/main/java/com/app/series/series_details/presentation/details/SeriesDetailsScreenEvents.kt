package com.app.series.series_details.presentation.details

import com.app.series.main.domain.models.Genre


sealed class SeriesDetailsScreenEvents {

    data class SetDataAndLoad(
        val moviesGenresList: List<Genre>,
        val tvGenresList: List<Genre>,
        val id: Int,
        val type: String,
        val category: String
    ) : SeriesDetailsScreenEvents()

    object Refresh : SeriesDetailsScreenEvents()
}