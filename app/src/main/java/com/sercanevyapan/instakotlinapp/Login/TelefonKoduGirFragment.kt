package com.sercanevyapan.instakotlinapp.Login


import android.content.Context

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

import com.sercanevyapan.instakotlinapp.R
import com.sercanevyapan.instakotlinapp.utils.EventbusDataEvents
import kotlinx.android.synthetic.main.fragment_telefon_kodu_gir.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.concurrent.TimeUnit






/**
 * A simple [Fragment] subclass.
 *
 */
class TelefonKoduGirFragment : Fragment() {

    var gelenTelNo = ""
    lateinit var mCallbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks
    var verificationID = ""
    var gelenKod=""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view= inflater.inflate(R.layout.fragment_telefon_kodu_gir, container, false)

        view.tvKullaniciTelNo.setText(gelenTelNo)

        setupCallBack()

        view.btnTelKodIleri.setOnClickListener {

            if(gelenKod.equals(view.etOnayKodu.text.toString())){
                var transaction=activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.loginContainer,KayitFragment())
                transaction.addToBackStack("kayitFragmentEklendi")
                transaction.commit()
            }else{
                var transaction=activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.loginContainer,KayitFragment())
                transaction.addToBackStack("kayitFragmentEklendi")
                transaction.commit()
                Toast.makeText(activity,"Kod Hatali",Toast.LENGTH_SHORT).show()
            }

        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            gelenTelNo,        // Phone number to verify
            60,                 // Timeout duration
            TimeUnit.SECONDS,   // Unit of timeout
            this!!.activity!!,               // Activity (for callback binding)
            mCallbacks)        // OnVerificationStateChangedCallbacks

        return view
    }

    private fun setupCallBack() {
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                gelenKod = credential.smsCode!!
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.e("HATA", "Hata çıktı: "+e.message)
            }

            override fun onCodeSent(
                verificationId: String?,
                token: PhoneAuthProvider.ForceResendingToken){
                verificationID=verificationId!!
            }
            
        }
    }

    @Subscribe (sticky = true)
    internal fun onTelefonNoEvent(telefonNumarasi : EventbusDataEvents.TelefonNoGonder){
        gelenTelNo = telefonNumarasi.telNo
        Log.e("sercan","Gelen No"+gelenTelNo)
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


