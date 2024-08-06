package com.app.series.series_details.data.repository

import com.app.series.main.data.local.media.SeriesDatabase
import com.app.series.main.data.mappers.toMedia
import com.app.series.main.domain.models.Series
import com.app.series.series_details.data.remote.api.DetailsApi
import com.app.series.series_details.data.remote.dto.details.DetailsDto
import com.app.series.series_details.domain.repository.DetailsRepository
import com.app.series.util.Constants
import com.app.series.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailsRepositoryImpl @Inject constructor(
    private val detailsApi: DetailsApi,
    seriesDb: SeriesDatabase
) : DetailsRepository {

    private val mediaDao = seriesDb.seriesDao

    override suspend fun getDetails(
        type: String,
        isRefresh: Boolean,
        id: Int,
        apiKey: String
    ): Flow<Resource<Series>> {

        return flow {

            emit(Resource.Loading(true))

            val mediaEntity = mediaDao.getSeriesById(id = id)

            val doDetailsExist = !(mediaEntity.runtime == null ||
                    mediaEntity.status == null || mediaEntity.tagline == null)

            if (!isRefresh && doDetailsExist) {
                emit(
                    Resource.Success(
                        data = mediaEntity.toMedia(
                            type = mediaEntity.mediaType ?: Constants.TV,
                            category = mediaEntity.category ?: Constants.POPULAR
                        )
                    )
                )

                emit(Resource.Loading(false))
                return@flow
            }

            val remoteDetails = fetchRemoteForDetails(
                type = mediaEntity.mediaType ?: Constants.TV,
                id = id,
                apiKey = apiKey
            )

            if (remoteDetails == null) {emit(
                Resource.Success(
                    data = mediaEntity.toMedia(
                        type = mediaEntity.mediaType ?: Constants.TV,
                        category = mediaEntity.category ?: Constants.POPULAR
                    )
                )
            )
                emit(Resource.Loading(false))
                return@flow
            }

            remoteDetails.let { details ->

                mediaEntity.runtime = details.runtime
                mediaEntity.status = details.status
                mediaEntity.tagline = details.tagline

                mediaDao.updateSeriesItem(mediaEntity)

                emit(
                    Resource.Success(
                        data = mediaEntity.toMedia(
                            type = mediaEntity.mediaType ?: Constants.TV,
                            category = mediaEntity.category ?: Constants.POPULAR
                        )
                    )
                )

                emit(Resource.Loading(false))

            }


        }

    }

    private suspend fun fetchRemoteForDetails(
        type: String,
        id: Int,
        apiKey: String
    ): DetailsDto? {
        val remoteDetails = try {
            detailsApi.getDetails(
                type = type,
                id = id,
                apiKey = apiKey
            )
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: HttpException) {
            e.printStackTrace()
            null
        }

        return remoteDetails

    }

}










