package com.app.series.main.data.repository

import com.app.series.main.data.local.media.SeriesDatabase
import com.app.series.main.data.mappers.toMedia
import com.app.series.main.data.mappers.toMediaEntity
import com.app.series.main.data.remote.api.SeriesApi
import com.app.series.main.domain.models.Series
import com.app.series.main.domain.repository.SeriesRepository
import com.app.series.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeriesRepositoryImpl @Inject constructor(
    private val seriesApi: SeriesApi,
    mediaDb: SeriesDatabase
) : SeriesRepository {

    private val mediaDao = mediaDb.seriesDao

    override suspend fun insertItem(series: Series) {
        val mediaEntity = series.toMediaEntity()

        mediaDao.insertSeriesItem(
            mediaItem = mediaEntity
        )
    }

    override suspend fun getItem(
        id: Int,
        type: String,
        category: String,
    ): Series {
        return mediaDao.getSeriesById(id).toMedia(
            category = category,
            type = type
        )
    }

    override suspend fun updateItem(series: Series) {
        val mediaEntity = series.toMediaEntity()

        mediaDao.updateSeriesItem(
            mediaItem = mediaEntity
        )

    }

    override suspend fun getMoviesAndTvSeriesList(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        type: String,
        category: String,
        page: Int): Flow<Resource<List<Series>>> {
        return flow {

            emit(Resource.Loading(true))

            val localMediaList = mediaDao.getMediaListByTypeAndCategory(type, category)

            val shouldJustLoadFromCache =
                localMediaList.isNotEmpty() && !fetchFromRemote && !isRefresh
            if (shouldJustLoadFromCache) {

                emit(Resource.Success(
                    data = localMediaList.map {
                        it.toMedia(
                            type = type,
                            category = category
                        )
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            var searchPage = page
            if (isRefresh) {
                mediaDao.deleteMediaByTypeAndCategory(type, category)
                searchPage = 1
            }

            val remoteMediaList = try {
                seriesApi.getMoviesAndTvSeriesList(
                    type, category, searchPage
                ).results
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                return@flow
            }

            remoteMediaList.let { mediaList ->
                val media = mediaList.map {
                    it.toMedia(
                        type = type,
                        category = category
                    )
                }

                val entities = mediaList.map {
                    it.toMediaEntity(
                        type = type,
                        category = category,
                    )
                }

                mediaDao.insertSeriesList(entities)

                emit(
                    Resource.Success(data = media)
                )
                emit(Resource.Loading(false))
            }
        }
    }

}










