package com.app.series.main.data.local.media

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SeriesEntity::class],
    version = 1
)
abstract class SeriesDatabase: RoomDatabase() {
    abstract val seriesDao: SeriesDao
}