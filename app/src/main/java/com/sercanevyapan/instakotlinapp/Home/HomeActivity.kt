package com.sercanevyapan.instakotlinapp.Home

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.nostra13.universalimageloader.core.ImageLoader
import com.sercanevyapan.instakotlinapp.Login.LoginActivity
import com.sercanevyapan.instakotlinapp.R
import com.sercanevyapan.instakotlinapp.utils.BottomNavigationViewHelper
import com.sercanevyapan.instakotlinapp.utils.HomePagerAdapter
import com.sercanevyapan.instakotlinapp.utils.UniversalImageLoader
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val ACTIVITY_NO=0
    private val TAG="HomeActivity"

    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        setupAuthListener()
        mAuth = FirebaseAuth.getInstance()

        initImageLoader()
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

    private fun initImageLoader(){

        var universalImageLoader= UniversalImageLoader(this)
        ImageLoader.getInstance().init(universalImageLoader.config)

    }

    private fun setupAuthListener() {
        mAuthListener=object :FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user = FirebaseAuth.getInstance().currentUser

                if(user == null){
                    var intent= Intent(this@HomeActivity,LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK )
                    startActivity(intent)
                    finish()
                }else{

                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        if(mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener)
        }
    }
}
