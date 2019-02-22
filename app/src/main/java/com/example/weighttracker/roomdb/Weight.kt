package com.example.weighttracker.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weights")

data class Weight(

    val weight: Double,
    @PrimaryKey
    val date: String
//    @PrimaryKey(autoGenerate = true)
//    val id: Int = 0
)