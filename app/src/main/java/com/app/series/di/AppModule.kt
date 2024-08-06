package com.app.series.di

import android.app.Application
import androidx.room.Room
import com.app.series.main.data.local.media.SeriesDatabase
import com.app.series.main.data.remote.api.SeriesApi
import com.app.series.main.data.remote.api.SeriesApi.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()


    @Provides
    @Singleton
    fun provideMediaDatabase(app: Application): SeriesDatabase {
        return Room.databaseBuilder(
            app,
            SeriesDatabase::class.java,
            "mediadb.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideSeriesApi() : SeriesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(SeriesApi::class.java)
    }

}









