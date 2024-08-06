package com.app.series.util.ui_shared_components

import androidx.compose.runtime.Composable
import com.app.series.main.domain.models.Genre

@Composable
fun genresProvider(
    genre_ids: List<Int>,
    allGenres: List<Genre>
): String {


    var genres = ""

    for (i in allGenres.indices) {
        for (j in genre_ids.indices) {
            if (allGenres[i].id == genre_ids[j]) {
                genres += "${allGenres[i].name} - "
            }
        }
    }

    return genres.dropLastWhile { it == ' ' || it == '-' }
}













