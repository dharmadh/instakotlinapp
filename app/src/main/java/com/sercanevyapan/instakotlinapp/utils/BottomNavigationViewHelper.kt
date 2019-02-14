package com.sercanevyapan.instakotlinapp.utils

import android.content.Context
import android.content.Intent
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.sercanevyapan.instakotlinapp.Home.HomeActivity
import com.sercanevyapan.instakotlinapp.News.NewsActivity
import com.sercanevyapan.instakotlinapp.Profile.ProfileActivity
import com.sercanevyapan.instakotlinapp.R
import com.sercanevyapan.instakotlinapp.Search.SearchActivity
import com.sercanevyapan.instakotlinapp.Share.ShareActivity
import java.security.AccessControlContext

class BottomNavigationViewHelper {

    companion object {

        fun setupBottomNavigationView(bottomNavigationViewEx: BottomNavigationViewEx){

            bottomNavigationViewEx.enableAnimation(false)
            bottomNavigationViewEx.enableItemShiftingMode(false)
            bottomNavigationViewEx.enableShiftingMode(false)
            bottomNavigationViewEx.setTextVisibility(false)

        }

        fun setupNavigation(context: Context, bottomNavigationViewEx: BottomNavigationViewEx){

            bottomNavigationViewEx.onNavigationItemSelectedListener=object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {

                    when(item.itemId){
                        R.id.ic_home -> {

                            val intent = Intent(context,HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            context.startActivity(intent)
                            return true
                        }

                        R.id.ic_search -> {

                            val intent = Intent(context,SearchActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            context.startActivity(intent)
                            return true
                        }

                        R.id.ic_share -> {

                            val intent = Intent(context,ShareActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            context.startActivity(intent)
                            return true
                        }

                        R.id.ic_news -> {

                            val intent = Intent(context,NewsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            context.startActivity(intent)
                            return true
                        }

                        R.id.ic_profile -> {

                            val intent = Intent(context,ProfileActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            context.startActivity(intent)
                            return true
                        }

                    }
                    return false

                }


            }




        }

    }

}