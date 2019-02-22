package com.example.weighttracker.repository

import androidx.lifecycle.LiveData
import com.example.weighttracker.roomdb.*

interface Repository{
    fun getWeights(): LiveData<List<Weight>>

    fun updateWeight(weight: Weight)

    fun insertWeight(weight: Weight)

    fun deleteWeight(weight: Weight)
}