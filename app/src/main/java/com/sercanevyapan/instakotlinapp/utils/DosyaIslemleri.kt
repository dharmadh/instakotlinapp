package com.sercanevyapan.instakotlinapp.utils

import android.util.Log
import java.io.File

class DosyaIslemleri {

    companion object {
        fun klasordekiDosyalariGetir(klasorAdi:String):ArrayList<String>{

            var tumDosyalar=ArrayList<String>()

            var file= File(klasorAdi)

            //parametre olarak gönderdiğimiz klasördeki tüm dosyalar alınır
            var klasordekiTumDosyalar = file.listFiles()
            //parametre olarak gönderdiğimiz klasör yokunda eleman olup olmadığı kontrol edildi
            if(klasordekiTumDosyalar !=null){

                for(i in 0.. klasordekiTumDosyalar.size-1){

                    //sadece dosyalara bakılır
                    if(klasordekiTumDosyalar[i].isFile){

                        Log.e("HATA","okunan veri bir dosya" )

                        //okuduğumuz dosyanın telefondaki yeri ve adını içerir
                        //files//root/logo.png
                        var okunanDosyaYolu:String=klasordekiTumDosyalar[i].absolutePath
                        var dosyaTuru:String = okunanDosyaYolu.substring(okunanDosyaYolu.lastIndexOf("."))

                        if(dosyaTuru.equals(".jpg") || dosyaTuru.equals(".jpeg") || dosyaTuru.equals(".png") || dosyaTuru.equals(".mp4")){

                            tumDosyalar.add(okunanDosyaYolu)

                        }

                    }
                }

            }

            return tumDosyalar


        }
    }

}