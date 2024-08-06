package com.app.series.main.data.local.media

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SeriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeriesList(
        mediaEntities: List<SeriesEntity>
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeriesItem(
        mediaItem: SeriesEntity
    )

    @Update
    suspend fun updateSeriesItem(
        mediaItem: SeriesEntity
    )

    @Query(
        """
            DELETE FROM seriesentity 
            WHERE mediaType = :mediaType AND category = :category
        """
    )
    suspend fun deleteMediaByTypeAndCategory(mediaType: String, category: String)

    @Query("SELECT * FROM seriesentity WHERE id = :id")
    suspend fun getSeriesById(id: Int): SeriesEntity

    @Query(
        """
            SELECT * 
            FROM seriesentity 
            WHERE mediaType = :mediaType AND category = :category
        """
    )
    suspend fun getMediaListByTypeAndCategory(
        mediaType: String, category: String
    ): List<SeriesEntity>

    @Query(
        """
            DELETE FROM seriesentity 
            WHERE category = :category
        """
    )
    suspend fun deleteTrendingMediaList(category: String)


    @Query(
        """
            SELECT * 
            FROM seriesentity 
            WHERE category = :category
        """
    )
    suspend fun getTrendingMediaList(category: String): List<SeriesEntity>


}