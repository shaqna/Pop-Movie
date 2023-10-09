package com.maou.popmovie.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maou.popmovie.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movies: List<MovieEntity>)
    @Query("SELECT * FROM movie LIMIT 10")
    fun selectAllMovie(): Flow<List<MovieEntity>>

}