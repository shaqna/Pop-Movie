package com.maou.popmovie.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "moviedb")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val title: String = "",
    val originalTitle: String = "",
    val releaseDate: String = "",
    val overview: String = ""
)
