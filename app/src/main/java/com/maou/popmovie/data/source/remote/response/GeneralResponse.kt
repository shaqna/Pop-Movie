package com.maou.popmovie.data.source.remote.response

data class GeneralResponse<T>(
    val results: List<T>
)
