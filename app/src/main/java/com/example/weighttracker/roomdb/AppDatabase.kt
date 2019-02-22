package com.example.weighttracker.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 1,
    entities = [Weight::class])

@TypeConverters()
abstract class AppDatabase : RoomDatabase() {
    abstract fun weightDao(): WeightDao
}