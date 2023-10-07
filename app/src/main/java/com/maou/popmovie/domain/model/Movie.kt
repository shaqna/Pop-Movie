package com.maou.popmovie.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val releaseDate: String,
    val overview: String
)
