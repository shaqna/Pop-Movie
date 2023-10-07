package com.maou.popmovie.data.source.remote.response

import com.squareup.moshi.Json

data class MovieResponse(
    val id: Int = 0,
    val title: String = "",
    @field:Json(name = "original_title")
    val originalTitle: String = "",
    @field:Json(name = "release_date")
    val releaseDate: String = "",
    val overview: String = ""
)