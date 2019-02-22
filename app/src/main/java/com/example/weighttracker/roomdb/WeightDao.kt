package com.example.weighttracker.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeightDao{

    @Query("SELECT * FROM weights")
    fun getWeights(): LiveData<List<Weight>>

    @Update
    fun update(weight: Weight)

    @Insert
    fun insert(weight: Weight)

    @Delete
    fun delete(weight: Weight)
}