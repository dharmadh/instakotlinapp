package com.sercanevyapan.instakotlinapp.Login


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.sercanevyapan.instakotlinapp.Models.UserDetails
import com.sercanevyapan.instakotlinapp.Models.Users

import com.sercanevyapan.instakotlinapp.R
import com.sercanevyapan.instakotlinapp.utils.EventbusDataEvents
import kotlinx.android.synthetic.main.fragment_kayit.*
import kotlinx.android.synthetic.main.fragment_kayit.view.*
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
    var emailIleKayitIslemi=true
    lateinit var mAuth : FirebaseAuth
    lateinit var mRef : DatabaseReference
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater!!.inflate(R.layout.fragment_kayit, container, false)

        progressBar=view.pbKullaniciKayit

        mAuth = FirebaseAuth.getInstance()

        mRef= FirebaseDatabase.getInstance().reference


        view.tvGirisYap.setOnClickListener {
            var intent = Intent(activity,LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        view.etAdSoyad.addTextChangedListener(watcher)
        view.etKullaniciAdi.addTextChangedListener(watcher)
        view.etSifreLogin.addTextChangedListener(watcher)

        view.btnGiris.setOnClickListener {

            var userNameKullanimdaMi=false

            mRef.child("users").addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot?) {
                    if(p0!!.getValue()!=null){
                        for(user in p0!!.children){
                            var okunanKullanici=user.getValue(Users::class.java)
                            if(okunanKullanici!!.user_name!!.equals(view.etKullaniciAdi.text.toString())){
                                Toast.makeText(activity,"Kullanıcı adı kullanımda",Toast.LENGTH_SHORT).show()
                                userNameKullanimdaMi=true
                                break
                            }
                        }
                        if(userNameKullanimdaMi==false){
                            progressBar.visibility=View.VISIBLE

                            // kullanıcı email ile kaydolmak istiyor
                            if(emailIleKayitIslemi){

                                var sifre=view.etSifreLogin.text.toString()
                                var adSoyad=view.etAdSoyad.text.toString()
                                var userName=view.etKullaniciAdi.text.toString()


                                mAuth.createUserWithEmailAndPassword(gelenEmail,sifre)
                                    .addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                                        override fun onComplete(p0: Task<AuthResult>) {
                                            if(p0!!.isSuccessful){
                                                Toast.makeText(activity,"Oturum email ile açıldı:"+mAuth.currentUser!!.uid,Toast.LENGTH_SHORT).show()

                                                var userID=mAuth.currentUser!!.uid.toString()

                                                // oturum açan kullanıcının verilerini database'e kaydedelim...
                                                var kaydedilecekKullaniciDetaylari=UserDetails("0","0","0","","","")
                                                var kaydedilecekKullanici= Users(gelenEmail,sifre,userName,adSoyad,"","",userID,kaydedilecekKullaniciDetaylari)

                                                mRef.child("users").child(userID).setValue(kaydedilecekKullanici)
                                                    .addOnCompleteListener(object : OnCompleteListener<Void>{
                                                        override fun onComplete(p0: Task<Void>) {
                                                            if(p0!!.isSuccessful){
                                                                Toast.makeText(activity,"Kullanıcı kaydedildi",Toast.LENGTH_SHORT).show()
                                                                progressBar.visibility=View.INVISIBLE
                                                            }else{
                                                                progressBar.visibility=View.INVISIBLE
                                                                mAuth.currentUser!!.delete()
                                                                    .addOnCompleteListener(object : OnCompleteListener<Void>{
                                                                        override fun onComplete(p0: Task<Void>) {
                                                                            if(p0!!.isSuccessful){
                                                                                Toast.makeText(activity,"Kullanıcı kaydedilemedi, tekrar deneyin",Toast.LENGTH_SHORT).show()
                                                                            }
                                                                        }

                                                                    })
                                                            }
                                                        }
                                                    })
                                            }else{
                                                progressBar.visibility=View.INVISIBLE
                                                Toast.makeText(activity,"Oturum açılamadı :"+p0!!.exception,Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                    })
                                //kullanıcı telefon no ile kayıt olmak istiyor
                            }else{

                                var sifre=view.etSifreLogin.text.toString()
                                var sahteEmail = telNo + "@sercan.com"
                                var adSoyad=view.etAdSoyad.text.toString()
                                var userName=view.etKullaniciAdi.text.toString()
                                mAuth.createUserWithEmailAndPassword(sahteEmail,sifre)
                                    .addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                                        override fun onComplete(p0: Task<AuthResult>) {
                                            if(p0!!.isSuccessful){
                                                Toast.makeText(activity,"Oturum tel no ile açıldı Uid:"+mAuth.currentUser!!.uid,Toast.LENGTH_SHORT).show()

                                                var userID=mAuth.currentUser!!.uid.toString()
                                                // oturum açan kullanıcının verilerini database'e kaydedelim...
                                                var kaydedilecekKullaniciDetaylari=UserDetails("0","0","0","","","")
                                                var kaydedilecekKullanici= Users("",sifre,userName,adSoyad,telNo,sahteEmail,userID,kaydedilecekKullaniciDetaylari)

                                                mRef.child("users").child(userID).setValue(kaydedilecekKullanici)
                                                    .addOnCompleteListener(object : OnCompleteListener<Void>{
                                                        override fun onComplete(p0: Task<Void>) {
                                                            if(p0!!.isSuccessful){
                                                                Toast.makeText(activity,"Kullanıcı kaydedildi",Toast.LENGTH_SHORT).show()
                                                                progressBar.visibility=View.INVISIBLE
                                                            }else{
                                                                progressBar.visibility=View.INVISIBLE
                                                                mAuth.currentUser!!.delete()
                                                                    .addOnCompleteListener(object : OnCompleteListener<Void>{
                                                                        override fun onComplete(p0: Task<Void>) {
                                                                            if(p0!!.isSuccessful){
                                                                                Toast.makeText(activity,"Kullanıcı kaydedilemedi, tekrar deneyin",Toast.LENGTH_SHORT).show()
                                                                            }
                                                                        }

                                                                    })
                                                            }
                                                        }
                                                    })
                                            }else{
                                                progressBar.visibility=View.INVISIBLE
                                                Toast.makeText(activity,"Oturum açılamadı :"+p0!!.exception,Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                    })

                            }
                        }
                    }
                }


            })









        }

        return view
    }

    var watcher : TextWatcher = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(s!!.length>5){

                if(etAdSoyad.text.toString().length>5 && etKullaniciAdi.text.toString().length>5 && etSifreLogin.text.toString().length>5){

                    btnGiris.isEnabled=true
                    btnGiris.setTextColor(ContextCompat.getColor(activity!!,R.color.beyaz))
                    btnGiris.setBackgroundResource(R.drawable.register_button_aktif)

                }else{
                    btnGiris.isEnabled=false
                    btnGiris.setTextColor(ContextCompat.getColor(activity!!,R.color.sonukmavi))
                    btnGiris.setBackgroundResource(R.drawable.register_button)
                }

            }else{
                btnGiris.isEnabled=false
                btnGiris.setTextColor(ContextCompat.getColor(activity!!,R.color.sonukmavi))
                btnGiris.setBackgroundResource(R.drawable.register_button)
            }
        }

    }


    /////////////// EVENTBUS /////////////////////////////
    @Subscribe(sticky = true)
    internal fun onKayitEvent(kayitBilgileri : EventbusDataEvents.KayitBilgileriniGonder){

        if(kayitBilgileri.emailkayit==true){
            emailIleKayitIslemi=true
            gelenEmail = kayitBilgileri.email!!
            Log.e("sercan","Gelen tel no:"+gelenEmail)
            Toast.makeText(activity,"Gelen email: "+gelenEmail,Toast.LENGTH_SHORT).show()

        }else{
            emailIleKayitIslemi=false
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
    /////////////// EVENTBUS /////////////////////////////
}


