package com.sercanevyapan.instakotlinapp.Home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sercanevyapan.instakotlinapp.R
import com.sercanevyapan.instakotlinapp.utils.BottomNavigationViewHelper
import com.sercanevyapan.instakotlinapp.utils.HomePagerAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val ACTIVITY_NO=0
    private val TAG="HomeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupNavigationView()
        setupHomeViewPager()

    }

    fun setupNavigationView(){

        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView)
        BottomNavigationViewHelper.setupNavigation(this,bottomNavigationView)
        var menu=bottomNavigationView.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }

    private fun setupHomeViewPager() {
        var homePagerAdapter = HomePagerAdapter(supportFragmentManager)
        homePagerAdapter.addFragment(CameraFragment()) //id=0
        homePagerAdapter.addFragment(HomeFragment()) //id=1
        homePagerAdapter.addFragment(MesaggesFragment())//id=2

        //activity mainde bulunan viewpagera oluşturduğumuz adaptörü atadık
        homeViewPager.adapter=homePagerAdapter

        //viewpagerın homefragment ile başlamasını sağladık
        homeViewPager.setCurrentItem(1)
    }
}
