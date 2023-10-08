package com.maou.popmovie.data.mapper

import com.maou.popmovie.data.source.local.entity.MovieEntity
import com.maou.popmovie.data.source.remote.response.MovieResponse
import com.maou.popmovie.domain.model.Movie

@JvmName(name = "toMovieEntity")
fun MovieResponse.toEntity(): MovieEntity =
    MovieEntity(
        id, title, originalTitle, releaseDate, overview
    )

@JvmName(name = "toListMovieEntity")
fun List<MovieResponse>.toListEntity(): List<MovieEntity> =
    map {
        it.toEntity()
    }

@JvmName(name = "toMovieModel")
fun MovieEntity.toModel(): Movie =
    Movie(
        id, title, originalTitle, releaseDate, overview
    )

@JvmName(name = "toListMovieModel")
fun List<MovieEntity>.toListModel(): List<Movie> =
    map {
        it.toModel()
    }