package com.sercanevyapan.instakotlinapp.Share


import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import com.sercanevyapan.instakotlinapp.R
import com.sercanevyapan.instakotlinapp.utils.DosyaIslemleri
import kotlinx.android.synthetic.main.fragment_share_gallery.*
import kotlinx.android.synthetic.main.fragment_share_gallery.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ShareGalleryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_share_gallery, container, false)

        var klasorPaths=ArrayList<String>()
        var klasorAdlari=ArrayList<String>()

        var root = Environment.getExternalStorageDirectory().path
        var kameraResimleri = root+"/DCIM/Camera"
        var indirilenResimler = root+"/Download"
        var whatsappResimleri = root+"/WhatsApp/Media/WhatsApp Images"

        klasorPaths.add(kameraResimleri)
        klasorPaths.add(indirilenResimler)
        klasorPaths.add(whatsappResimleri)

        klasorAdlari.add("Kamera")
        klasorAdlari.add("İndirilenler")
        klasorAdlari.add("Whatsapp")

        var spinnerArrayAdapter=ArrayAdapter(activity,android.R.layout.simple_spinner_item, klasorAdlari)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        view.spnKalsorAdlari.adapter=spinnerArrayAdapter

        var klasordekiDosyalar=DosyaIslemleri.klasordekiDosyalariGetir(kameraResimleri)

        for(str in klasordekiDosyalar){
            Log.e("HATA",str)
        }

        return view
    }


}
