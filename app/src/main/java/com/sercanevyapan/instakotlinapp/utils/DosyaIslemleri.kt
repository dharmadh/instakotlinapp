package com.sercanevyapan.instakotlinapp.utils

import android.util.Log
import java.io.File
import java.util.*

class DosyaIslemleri {

    companion object {
        fun klasordekiDosyalariGetir(klasorAdi:String):ArrayList<String>{

            var tumDosyalar=ArrayList<String>()

            var file= File(klasorAdi)

            //parametre olarak gönderdiğimiz klasördeki tüm dosyalar alınır
            var klasordekiTumDosyalar = file.listFiles()
            //parametre olarak gönderdiğimiz klasör yokunda eleman olup olmadığı kontrol edildi
            if(klasordekiTumDosyalar !=null){

                //galeriden getirilen resimler sonda başa listelenir
                if(klasordekiTumDosyalar.size>1){

                    Arrays.sort(klasordekiTumDosyalar, object : Comparator<File>{
                        override fun compare(o1: File?, o2: File?): Int {
                            if(o1!!.lastModified() > o2!!.lastModified()){
                                return -1
                            }else return 1
                        }


                    })

                }

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