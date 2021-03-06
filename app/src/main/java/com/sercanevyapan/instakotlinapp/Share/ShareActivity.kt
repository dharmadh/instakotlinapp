package com.sercanevyapan.instakotlinapp.Share

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sercanevyapan.instakotlinapp.R
import com.sercanevyapan.instakotlinapp.utils.BottomNavigationViewHelper
import com.sercanevyapan.instakotlinapp.utils.SharePagerAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_share.*

class ShareActivity : AppCompatActivity() {

    private val ACTIVITY_NO=2
    private val TAG="ShareActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        setupShareViewPager()



    }

    private fun setupShareViewPager() {

        var tabAdlari= ArrayList<String>()
        tabAdlari.add("GALERİ")
        tabAdlari.add("FOTOĞRAF")
        tabAdlari.add("VİDEO")

        var sharePagerAdapter= SharePagerAdapter(supportFragmentManager,tabAdlari)
        sharePagerAdapter.addFragment(ShareGalleryFragment())
        sharePagerAdapter.addFragment(ShareCameraFragment())
        sharePagerAdapter.addFragment(ShareVideoFragment())

        shareViewPager.adapter=sharePagerAdapter
        sharetablayout.setupWithViewPager(shareViewPager)


    }


}
