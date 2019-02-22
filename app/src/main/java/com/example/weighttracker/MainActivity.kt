package com.example.weighttracker

import android.os.PersistableBundle
import com.example.weighttracker.commons.openFragment
import com.example.weighttracker.fragments.WeightFragment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*


import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var fragment: Fragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        fragment = WeightFragment.newInstance()
        openFragment(fragment, supportFragmentManager)
        Log.d("BAJS", "BAJS")
    }

}
