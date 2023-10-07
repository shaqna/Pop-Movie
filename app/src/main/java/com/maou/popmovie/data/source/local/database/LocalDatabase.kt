package com.maou.popmovie.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maou.popmovie.data.source.local.dao.MovieDao
import com.maou.popmovie.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}