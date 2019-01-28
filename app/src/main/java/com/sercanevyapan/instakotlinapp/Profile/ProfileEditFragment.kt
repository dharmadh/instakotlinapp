package com.sercanevyapan.instakotlinapp.Profile


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nostra13.universalimageloader.core.ImageLoader

import com.sercanevyapan.instakotlinapp.R
import com.sercanevyapan.instakotlinapp.utils.UniversalImageLoader
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_profile_edit.view.*



/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileEditFragment : Fragment() {

    lateinit var circleProfileImageFragment:CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_profile_edit, container, false)

        circleProfileImageFragment=view.findViewById(R.id.circleProfileImage)

        initImageLoader()
        setupProfilePicture()

        view.imgClose.setOnClickListener {
            activity!!.onBackPressed()
        }

        return view
    }

    private fun initImageLoader(){

        var universalImageLoader=UniversalImageLoader(activity!!)
        ImageLoader.getInstance().init(universalImageLoader.config)

    }

    private fun setupProfilePicture() {
        //http://icons.iconarchive.com/icons/danleech/simple/1024/android-icon.png

        var imgURL = "icons.iconarchive.com/icons/danleech/simple/1024/android-icon.png"
        UniversalImageLoader.setImage(imgURL,circleProfileImageFragment,null,"http://")
    }


}
