package com.sercanevyapan.instakotlinapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sercanevyapan.instakotlinapp.utils.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupNavigationView()

    }

    fun setupNavigationView(){

        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView)

    }

}
