package com.app.series.main.data.mappers

import com.app.series.main.data.local.media.SeriesEntity
import com.app.series.main.data.remote.dto.SeriesDto
import com.app.series.main.domain.models.Series
import com.app.series.util.Constants


fun SeriesEntity.toMedia(
    type: String,
    category: String
): Series {
    return Series(
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = try {
            genreIds.split(",").map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1, -2)
        },
        id = id,
        adult = adult,
        mediaType = type,
        originCountry = try {
            originCountry.split(",").map { it }
        } catch (e: Exception) {
            listOf("-1", "-2")
        },
        originalTitle = originalTitle,
        category = category,
        runtime = runtime,
        status = status,
        tagline = tagline,
        videos = try {
            videos.split(",").map { it }
        } catch (e: Exception) {
            listOf("-1", "-2")
        },
        similarMediaList = try {
            similarMediaList.split(",").map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1, -2)
        },
    )
}

fun SeriesDto.toMediaEntity(
    type: String,
    category: String,
): SeriesEntity {
    return SeriesEntity(
        backdropPath = backdrop_path ?: Constants.unavailable,
        originalLanguage = original_language ?: Constants.unavailable,
        overview = overview ?: Constants.unavailable,
        posterPath = poster_path ?: Constants.unavailable,
        releaseDate = release_date ?: "-1,-2",
        title = title ?: name ?: Constants.unavailable,
        originalName = original_name ?: Constants.unavailable,
        voteAverage = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        voteCount = vote_count ?: 0,
        genreIds = try {
            genre_ids?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        id = id ?: 1,
        adult = adult ?: false,
        mediaType = type,
        category = category,
        originCountry = try {
            origin_country?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        originalTitle = original_title ?: original_name ?: Constants.unavailable,
        videos = try {
            videos?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        similarMediaList = try {
            similarMediaList?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        firstAirDate = first_air_date ?: "",
        video = video ?: false,

        status = "",
        runtime = 0,
        tagline = "",
    )
}


fun SeriesDto.toMedia(
    type: String,
    category: String,
): Series {
    return Series(
        backdropPath = backdrop_path ?: Constants.unavailable,
        originalLanguage = original_language ?: Constants.unavailable,
        overview = overview ?: Constants.unavailable,
        posterPath = poster_path ?: Constants.unavailable,
        releaseDate = release_date ?: Constants.unavailable,
        title = title ?: name ?: Constants.unavailable,
        voteAverage = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        voteCount = vote_count ?: 0,
        genreIds = genre_ids ?: emptyList(),
        id = id ?: 1,
        adult = adult ?: false,
        mediaType = type,
        category = category,
        originCountry = origin_country ?: emptyList(),
        originalTitle = original_title ?: original_name ?: Constants.unavailable,
        runtime = null,
        status = null,
        tagline = null,
        videos = videos,
        similarMediaList = similarMediaList ?: emptyList()
    )
}

fun Series.toMediaEntity(): SeriesEntity {
    return SeriesEntity(
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        popularity = popularity,
        voteCount = voteCount,
        genreIds = try {
            genreIds.joinToString(",")
        } catch (e: Exception) {
            "-1,-2"
        },
        id = id,
        adult = adult,
        mediaType = mediaType,
        originCountry = try {
            originCountry.joinToString(",")
        } catch (e: Exception) {
            "-1,-2"
        },
        originalTitle = originalTitle,
        category = category,
        videos = try {
            videos?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        },
        similarMediaList = try {
            similarMediaList.joinToString(",")
        } catch (e: Exception) {
            "-1,-2"
        },
        video = false,
        firstAirDate = releaseDate,
        originalName = originalTitle,

        status = status ?: "",
        runtime = runtime ?: 0,
        tagline = tagline ?: ""
    )
}






