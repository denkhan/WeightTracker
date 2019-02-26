package com.example.weighttracker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.weighttracker.roomdb.Weight
import com.example.weighttracker.R.layout.fragment_weight
import com.example.weighttracker.viewmodels.WeightViewModel
import kotlinx.android.synthetic.main.fragment_weight.*
import androidx.lifecycle.Observer
import com.example.weighttracker.commons.withModels
import com.example.weighttracker.epoxyDate
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class WeightFragment : Fragment() {
    private val weightViewModel: WeightViewModel by viewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? = inflater.inflate(fragment_weight, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        insert_btn.setOnClickListener {
            val weight = enterWeight.text.toString()
            if(!weight.equals("")){
                if(!weightViewModel.insertWeight(weight.toDouble()))
                    buildDeleteAlertView(weight.toDouble())
            }
        }

        setUpGraph()
        weightViewModel.getAvailableWeights().observe(this,
            Observer { weightList -> setUpRecyclerView(weightList)})

    }

    fun setUpGraph(){
        graph.viewport.isXAxisBoundsManual = true
        graph.title = "Weight Graph"

        weightViewModel.getAvailableWeights().observe(this,
            Observer {  weightList ->
                graph.removeAllSeries()
                graph.viewport.setMaxX(
                    abs(weightList.reversed()
                    .groupBy { it.date }
                    .count())-1.0)
                graph.addSeries(weightViewModel
                    .createGraph(weightList))})
    }

    private fun setUpRecyclerView(weights: List<Weight>) {
        recycler_view_statistic.withModels {
            weights.sortedBy { it.date }.reversed().groupBy { it.date }
                .forEach {
                    epoxyDate {
                        id(it.key)
                        dateText(it.key)
                        val d = it.value.map { it.weight }.average()
                        weightText(d.toString())
//                        onLongClick { _ ->
//                            buildDeleteWeightAlertView(it.value.get(0))
//                            true}
                    }
                }
        }
    }
    private fun buildDeleteAlertView(weight: Double) = context?.let {
        AlertDialog.Builder(context!!)
            .setTitle("You have already inserted a weight today. Would you like to update it?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Yes!") { _, b ->
                if (b == AlertDialog.BUTTON_POSITIVE) weightViewModel.updateWeight(weight)
            }
            .show()
    }

//    private fun buildDeleteWeightAlertView(weight: Weight) = context?.let {
//        AlertDialog.Builder(context!!)
//            .setTitle("Would you like to remove this weight?\n" +
//                      weight.date + " " + weight.weight)
//            .setNegativeButton("Cancel", null)
//            .setPositiveButton("Yes!") { _, b ->
//                if (b == AlertDialog.BUTTON_POSITIVE) weightViewModel.deleteWeight(weight)
//            }
//            .show()
//    }

    companion object {
        @JvmStatic
        fun newInstance() = WeightFragment()
    }


}