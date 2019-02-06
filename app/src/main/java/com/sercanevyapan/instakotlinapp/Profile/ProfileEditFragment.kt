package com.sercanevyapan.instakotlinapp.Profile


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nostra13.universalimageloader.core.ImageLoader
import com.sercanevyapan.instakotlinapp.Models.Users

import com.sercanevyapan.instakotlinapp.R
import com.sercanevyapan.instakotlinapp.utils.EventbusDataEvents
import com.sercanevyapan.instakotlinapp.utils.UniversalImageLoader
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_profile_edit.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileEditFragment : Fragment() {

    lateinit var circleProfileImageFragment:CircleImageView
    lateinit var gelenKullaniciBilgileri:Users

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_profile_edit, container, false)

        setupKullaniciBilgileri(view)

        view.imgClose.setOnClickListener {
            activity!!.onBackPressed()
        }

        return view
    }

    private fun setupKullaniciBilgileri(view: View?) {

        view!!.etProfileName.setText(gelenKullaniciBilgileri!!.adi_soyadi)
        view!!.etUserName.setText(gelenKullaniciBilgileri!!.user_name)

        if(!gelenKullaniciBilgileri!!.user_detail!!.biography!!.isNullOrEmpty()){
            view!!.etUserBio.setText(gelenKullaniciBilgileri!!.user_detail!!.biography!!)
        }
        if(!gelenKullaniciBilgileri!!.user_detail!!.web_site!!.isNullOrEmpty()){
            view!!.etUserWebSite.setText(gelenKullaniciBilgileri!!.user_detail!!.web_site!!)
        }

        var imgUrl=gelenKullaniciBilgileri!!.user_detail!!.profile_picture
        UniversalImageLoader.setImage(imgUrl!!,view!!.circleProfileImage,view!!.progressBar,"")
    }
    

    /////////////// EVENTBUS /////////////////////////////
    @Subscribe(sticky = true)
    internal fun onKullaniciBilgileriEvent(kullaniciBilgileri : EventbusDataEvents.KullaniciBilgileriniGonder){

        gelenKullaniciBilgileri=kullaniciBilgileri!!.kullanici!!


    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

}
