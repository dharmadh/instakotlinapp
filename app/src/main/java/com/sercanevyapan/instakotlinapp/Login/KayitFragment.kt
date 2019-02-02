package com.sercanevyapan.instakotlinapp.Login


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.sercanevyapan.instakotlinapp.R
import com.sercanevyapan.instakotlinapp.utils.EventbusDataEvents
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class KayitFragment : Fragment() {

    var telNo=""
    var verificationID=""
    var gelenKod=""
    var gelenEmail=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater!!.inflate(R.layout.fragment_kayit, container, false)
    }

    @Subscribe(sticky = true)
    internal fun onKayitEvent(kayitBilgileri : EventbusDataEvents.KayitBilgileriniGonder){

        if(kayitBilgileri.emailkayit==true){
            gelenEmail = kayitBilgileri.email!!
            Log.e("sercan","Gelen tel no:"+gelenEmail)
            Toast.makeText(activity,"Gelen email: "+gelenEmail,Toast.LENGTH_SHORT).show()

        }else{
            telNo = kayitBilgileri.telNo!!
            verificationID = kayitBilgileri.verificationID!!
            gelenKod = kayitBilgileri.code!!

            Toast.makeText(activity,"Gelen kod: "+gelenKod+"Verification:" +verificationID,Toast.LENGTH_SHORT).show()
        }


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
