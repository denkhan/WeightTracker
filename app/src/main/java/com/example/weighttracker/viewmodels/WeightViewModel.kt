package com.example.weighttracker.viewmodels

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weighttracker.coroutineUtils.CoroutineContextProvider
import com.example.weighttracker.repository.Repository
import com.example.weighttracker.roomdb.Weight
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.sql.SQLException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext


class WeightViewModel(private val repo: Repository, private val coroutineContextProvider: CoroutineContextProvider) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = coroutineContextProvider.Main
    private val IO = coroutineContextProvider.IO

    private val existingWeights = repo.getWeights()


    fun getAvailableWeights(): LiveData<List<Weight>> = existingWeights

    fun insertWeight(weight: Double): Boolean{
        val current = Date().toSimpleString()
        val temp = Weight(weight, current)
        var b: Boolean = true
        b = runBlocking {
            return@runBlocking withContext(IO){
                try {
                    repo.insertWeight(temp)
                    return@withContext true
                }catch (exception: SQLiteException){
                    return@withContext false
                }
            }

        }
        return b
    }

//    fun deleteWeight(weight: Weight){
//
//    }

    fun updateWeight(weight: Double){
        val current = Date().toSimpleString()
        val temp = Weight(weight, current)
        launch {
            withContext(IO){
                repo.updateWeight(temp)
            }
        }
    }

    fun createGraph(list: List<Weight>): LineGraphSeries<DataPoint> {
        val temp = list
            ?.sortedBy { it.date }
            ?.groupBy { it.date } // (date -> List(exerciseOne, exerciseTwo...), date2 -> List())
            ?.values
            ?.map { weightListByDate -> weightListByDate.map { it.weight }.average() }
            ?.mapIndexed { index, weight -> DataPoint(index.toDouble(), weight) }
            ?.toTypedArray()

        return LineGraphSeries(temp)
    }
}

fun Date.toSimpleString() : String {
    val format = SimpleDateFormat("dd/MM/yyy")
    return format.format(this)
}
