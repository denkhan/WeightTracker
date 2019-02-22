package com.example.weighttracker.repository

import androidx.lifecycle.LiveData
import com.example.weighttracker.roomdb.Weight
import com.example.weighttracker.roomdb.WeightDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class RepositoryImpl(private val weightDao: WeightDao): Repository, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    //.revered()?
    override fun getWeights(): LiveData<List<Weight>> =
        weightDao.getWeights()


    override fun updateWeight(weight: Weight) =
        weightDao.update(weight)

    override fun insertWeight(weight: Weight) =
        weightDao.insert(weight)

    override fun deleteWeight(weight: Weight) =
            weightDao.delete(weight)

}