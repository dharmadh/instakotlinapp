package com.sercanevyapan.instakotlinapp.utils

import com.sercanevyapan.instakotlinapp.Models.Users

class EventbusDataEvents {

    internal class KayitBilgileriniGonder(var telNo:String?, var email: String?, var verificationID:String?, var code:String?, var emailkayit:Boolean)

    internal class KullaniciBilgileriniGonder(var kullanici: Users?)

}