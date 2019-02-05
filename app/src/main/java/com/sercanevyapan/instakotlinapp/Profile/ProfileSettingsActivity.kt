package com.sercanevyapan.instakotlinapp.Profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sercanevyapan.instakotlinapp.R
import com.sercanevyapan.instakotlinapp.utils.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_profile_settings.*


class ProfileSettingsActivity : AppCompatActivity() {

    private val ACTIVITY_NO=4
    private val TAG="ProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        setupNavigationView()
        setupToolbar()
        fragmentNavigations()
    }

    private fun fragmentNavigations() {
        tvProfiliDüzenleHesapAyarları.setOnClickListener {
            profileSettingsRoot.visibility=View.GONE
            var transaction=supportFragmentManager.beginTransaction()
            transaction.replace(R.id.profileSettingsContainer,ProfileEditFragment())
            transaction.addToBackStack("editProfileFragmentEklendi")
            transaction.commit()
        }

        tvCikisYap.setOnClickListener {
            var dialog=SignOutFragment()
            dialog.show(supportFragmentManager,"cikisYapDialogGoster")

        }
    }

    private fun setupToolbar() {
        imgBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        profileSettingsRoot.visibility=View.VISIBLE
        super.onBackPressed()
    }


    fun setupNavigationView(){

        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView)
        BottomNavigationViewHelper.setupNavigation(this,bottomNavigationView)
        var menu=bottomNavigationView.menu
        var menuItem=menu.getItem(ACTIVITY_NO)
        menuItem.setChecked(true)
    }
}
