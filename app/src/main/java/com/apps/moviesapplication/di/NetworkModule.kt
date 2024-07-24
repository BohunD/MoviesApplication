package com.apps.moviesapplication.di

import com.apps.moviesapplication.data.network.TmdbApiService
import com.apps.moviesapplication.data.repository.MovieInfoRepositoryImpl
import com.apps.moviesapplication.data.repository.MoviesRepositoryImpl
import com.apps.moviesapplication.domain.repository.MovieInfoRepository
import com.apps.moviesapplication.domain.repository.MoviesRepository
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
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTmdbApiService(retrofit: Retrofit): TmdbApiService {
        return retrofit.create(TmdbApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(apiService: TmdbApiService): MoviesRepository {
        return MoviesRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideMovieDetailsRepository(apiService: TmdbApiService): MovieInfoRepository {
        return MovieInfoRepositoryImpl(apiService)
    }
}
