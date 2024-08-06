package com.app.series.main.data.remote.dto

data class SeriesListDto(
    val page: Int,
    val results: List<SeriesDto>,
    val total_pages: Int,
    val total_results: Int
)