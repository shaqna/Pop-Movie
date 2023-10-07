package com.maou.popmovie.data.source.remote.service

import com.maou.popmovie.data.source.remote.response.GeneralResponse
import com.maou.popmovie.data.source.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String
    ): GeneralResponse<MovieResponse>
}