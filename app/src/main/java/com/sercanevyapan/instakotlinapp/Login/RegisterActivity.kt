package com.sercanevyapan.instakotlinapp.Login

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.TransactionTooLargeException
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.InputDevice
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.sercanevyapan.instakotlinapp.Home.HomeActivity
import com.sercanevyapan.instakotlinapp.Models.Users
import com.sercanevyapan.instakotlinapp.R
import com.sercanevyapan.instakotlinapp.utils.EventbusDataEvents
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus

class RegisterActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    lateinit var manager:FragmentManager
    lateinit var mRef:DatabaseReference
    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthListener:FirebaseAuth.AuthStateListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setupAuthListener()

        mAuth=FirebaseAuth.getInstance()
        mRef=FirebaseDatabase.getInstance().reference

        manager=supportFragmentManager
        manager.addOnBackStackChangedListener(this)

        init()
    }

    private fun init() {

        tvGirisYap.setOnClickListener {
            var intent = Intent(this@RegisterActivity,LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }

        tvEposta.setOnClickListener {
            viewTelefon.visibility= View.INVISIBLE
            viewEposta.visibility=View.VISIBLE
            etGirisYöntemi.setText("")
            etGirisYöntemi.inputType=InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            etGirisYöntemi.setHint("E-posta")

            btnIleri.isEnabled=false
            btnIleri.setTextColor(ContextCompat.getColor(this@RegisterActivity,R.color.sonukmavi))
            btnIleri.setBackgroundResource(R.drawable.register_button)
        }

        tvTelefon.setOnClickListener {
            viewTelefon.visibility= View.VISIBLE
            viewEposta.visibility=View.INVISIBLE
            etGirisYöntemi.setText("")
            etGirisYöntemi.inputType=InputType.TYPE_CLASS_NUMBER
            etGirisYöntemi.setHint("Telefon")

            btnIleri.isEnabled=false
            btnIleri.setTextColor(ContextCompat.getColor(this@RegisterActivity,R.color.sonukmavi))
            btnIleri.setBackgroundResource(R.drawable.register_button)
        }

        etGirisYöntemi.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.length>=10){

                    btnIleri.isEnabled=true
                    btnIleri.setTextColor(ContextCompat.getColor(this@RegisterActivity,R.color.beyaz))
                    btnIleri.setBackgroundResource(R.drawable.register_button_aktif)

                }else{
                    btnIleri.isEnabled=false
                    btnIleri.setTextColor(ContextCompat.getColor(this@RegisterActivity,R.color.sonukmavi))
                    btnIleri.setBackgroundResource(R.drawable.register_button)
                }
            }
        })

        btnIleri.setOnClickListener {
           if(etGirisYöntemi.hint.toString().equals("Telefon")){

               var ceptelefonuKullanimdaMi=false

               if(isValidTelefon(etGirisYöntemi.text.toString())){

                   mRef.child("users").addListenerForSingleValueEvent(object : ValueEventListener{
                       override fun onCancelled(p0: DatabaseError?) {

                       }

                       override fun onDataChange(p0: DataSnapshot?) {

                           if(p0!!.getValue()!=null){
                               for(user in p0!!.children){
                                   var okunanKullanici=user.getValue(Users::class.java)
                                   if(okunanKullanici!!.phone_number!!.equals(etGirisYöntemi.text.toString())){
                                       Toast.makeText(this@RegisterActivity,"Telefon numarası kullanımda",Toast.LENGTH_SHORT).show()
                                       ceptelefonuKullanimdaMi=true
                                       break
                                   }
                               }
                               if(ceptelefonuKullanimdaMi==false){
                                   loginRoot.visibility=View.GONE
                                   loginContainer.visibility=View.VISIBLE
                                   var transaction=supportFragmentManager.beginTransaction()
                                   transaction.replace(R.id.loginContainer,TelefonKoduGirFragment())
                                   transaction.addToBackStack("telefonKoduGirFragmentEklendi")
                                   transaction.commit()
                                   EventBus.getDefault().postSticky(EventbusDataEvents.KayitBilgileriniGonder(etGirisYöntemi.text.toString(),null,null,null,false))
                               }
                           }

                       }

                   })




               }else{
                   Toast.makeText(this, "Lütfen geçerli bir telefon numarası giriniz",Toast.LENGTH_SHORT).show()
               }


           }else{
               if(isValidEmail(etGirisYöntemi.text.toString())){

                   var emailKullanimdaMi=false

                   mRef.child("users").addListenerForSingleValueEvent(object : ValueEventListener{
                       override fun onCancelled(p0: DatabaseError?) {

                       }

                       override fun onDataChange(p0: DataSnapshot?) {
                           if(p0!!.getValue() != null){
                               for(user in p0!!.children){
                                   var okunanKullanici=user.getValue(Users::class.java)
                                   if(okunanKullanici!!.email!!.equals(etGirisYöntemi.text.toString())){
                                       Toast.makeText(this@RegisterActivity,"Email kullanımda",Toast.LENGTH_SHORT).show()
                                       emailKullanimdaMi=true
                                       break
                                   }
                               }
                               if(emailKullanimdaMi==false){
                                   loginRoot.visibility=View.GONE
                                   loginContainer.visibility=View.VISIBLE
                                   var transaction=supportFragmentManager.beginTransaction()
                                   transaction.replace(R.id.loginContainer, KayitFragment())
                                   transaction.addToBackStack("emailileGirisFragmentEklendi")
                                   transaction.commit()
                                   EventBus.getDefault().postSticky(EventbusDataEvents.KayitBilgileriniGonder(null,etGirisYöntemi.text.toString(),null,null,true))
                               }
                           }
                       }

                   })


               }else{
                   Toast.makeText(this, "Lütfen geçerli bir email giriniz",Toast.LENGTH_SHORT).show()
               }

           }
        }
    }

    override fun onBackStackChanged() {
        val elemanSayisi= manager.backStackEntryCount

        if(elemanSayisi==0){
            loginRoot.visibility=View.VISIBLE
        }
    }

    fun isValidEmail(kontrolEdilecekMail:String):Boolean{

        if(kontrolEdilecekMail == null){
            return false
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(kontrolEdilecekMail).matches()
    }

    fun isValidTelefon(kontrolEdilecekTelefon:String):Boolean{

        if(kontrolEdilecekTelefon == null || kontrolEdilecekTelefon.length > 14 ){
            return false
        }
        return android.util.Patterns.PHONE.matcher(kontrolEdilecekTelefon).matches()
    }

    private fun setupAuthListener() {
        mAuthListener=object : FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user = FirebaseAuth.getInstance().currentUser

                if(user != null){
                    var intent= Intent(this@RegisterActivity, HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
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
