package com.example.weighttracker.commons

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.weighttracker.R

fun openFragment(fragment: Fragment, supFragmentManager: FragmentManager){
    Log.d("BAJS2", fragment.view
        .toString())

    supFragmentManager.beginTransaction()
        .replace(R.id.fragment_layout, fragment)
        .commit()
}