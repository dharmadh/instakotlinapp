package com.sercanevyapan.instakotlinapp.Profile

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sercanevyapan.instakotlinapp.R
import com.sercanevyapan.instakotlinapp.utils.BottomNavigationViewHelper
import com.sercanevyapan.instakotlinapp.utils.UniversalImageLoader
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {

    private val ACTIVITY_NO=4
    private val TAG="ProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupToolbar()
        setupNavigationView()
        setupProfileFoto()

    }

    private fun setupProfileFoto() {
        val imgURL= "pixelz.cc/wp-content/uploads/2018/07/assassins-creed-odyssey-uhd-4k-wallpaper.jpg"
        UniversalImageLoader.setImage(imgURL,circleProfileImage,progressBar,"http://")
    }

    private fun setupToolbar() {
        imgProfileSettings.setOnClickListener{
            var intent= Intent(this,ProfileSettingsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        tvProfilDüzenleButon.setOnClickListener {
            profileRoot.visibility=View.GONE
            var transaction=supportFragmentManager.beginTransaction()
            transaction.replace(R.id.profileContainer,ProfileEditFragment())
            transaction.addToBackStack("editProfileFragmentEklendi")
            transaction.commit()
        }
    }

    fun setupNavigationView(){

        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView)
        BottomNavigationViewHelper.setupNavigation(this,bottomNavigationView)
        var menu=bottomNavigationView.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }

    override fun onBackPressed() {
        profileRoot.visibility=View.VISIBLE
        super.onBackPressed()
    }
}
