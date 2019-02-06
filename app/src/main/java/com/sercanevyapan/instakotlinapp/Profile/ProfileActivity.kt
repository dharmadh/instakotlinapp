package com.sercanevyapan.instakotlinapp.Profile

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.sercanevyapan.instakotlinapp.Login.LoginActivity
import com.sercanevyapan.instakotlinapp.Models.Users
import com.sercanevyapan.instakotlinapp.R
import com.sercanevyapan.instakotlinapp.utils.BottomNavigationViewHelper
import com.sercanevyapan.instakotlinapp.utils.EventbusDataEvents
import com.sercanevyapan.instakotlinapp.utils.UniversalImageLoader
import kotlinx.android.synthetic.main.activity_profile.*
import org.greenrobot.eventbus.EventBus


class ProfileActivity : AppCompatActivity() {

    private val ACTIVITY_NO=4
    private val TAG="ProfileActivity"

    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    lateinit var mUser:FirebaseUser
    lateinit var mRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupAuthListener()
        mAuth = FirebaseAuth.getInstance()
        mUser=mAuth.currentUser!!
        mRef=FirebaseDatabase.getInstance().reference


        setupToolbar()
        setupNavigationView()
        kullaniciBilgilriniGetir()
        setupProfileFoto()

    }

    private fun kullaniciBilgilriniGetir() {

        mRef.child("users").child(mUser!!.uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {

                if(p0!!.getValue()!=null){
                    var okunanKullanıcıBilgileri=p0!!.getValue(Users::class.java)

                    EventBus.getDefault().postSticky(EventbusDataEvents.KullaniciBilgileriniGonder(okunanKullanıcıBilgileri))

                    tvProfilAdiToolbar.setText(okunanKullanıcıBilgileri!!.user_name)
                    tvProfilGercekAd.setText(okunanKullanıcıBilgileri!!.adi_soyadi)
                    tvFollowerSayisi.setText(okunanKullanıcıBilgileri!!.user_detail!!.follower)
                    tvFollowingSayisi.setText(okunanKullanıcıBilgileri!!.user_detail!!.following)
                    tvPostSayisi.setText(okunanKullanıcıBilgileri!!.user_detail!!.post)

                    var imgURL:String=okunanKullanıcıBilgileri!!.user_detail!!.profile_picture!!
                    UniversalImageLoader.setImage(imgURL,circleProfileImage,progressBar,"")

                    if(!okunanKullanıcıBilgileri!!.user_detail!!.biography!!.isNullOrEmpty()){
                        tvBiyografi.visibility=View.VISIBLE
                        tvBiyografi.setText(okunanKullanıcıBilgileri!!.user_detail!!.biography!!)
                    }
                    if(!okunanKullanıcıBilgileri!!.user_detail!!.web_site!!.isNullOrEmpty()){
                        tvWebSitesi.visibility=View.VISIBLE
                        tvWebSitesi.setText(okunanKullanıcıBilgileri!!.user_detail!!.web_site!!)
                    }
                }

            }

        })

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

    private fun setupAuthListener() {



        mAuthListener=object :FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user = FirebaseAuth.getInstance().currentUser

                if(user == null){
                    var intent= Intent(this@ProfileActivity, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK )
                    startActivity(intent)
                    finish()
                }else{
                    mUser=mAuth.currentUser!!
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
