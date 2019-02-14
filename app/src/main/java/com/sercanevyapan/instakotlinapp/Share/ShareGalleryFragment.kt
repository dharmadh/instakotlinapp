package com.sercanevyapan.instakotlinapp.Share


import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.sercanevyapan.instakotlinapp.R
import com.sercanevyapan.instakotlinapp.utils.DosyaIslemleri
import com.sercanevyapan.instakotlinapp.utils.ShareActivityGridViewAdapter
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

        var view:View = inflater.inflate(R.layout.fragment_share_gallery, container, false)

        var klasorPaths=ArrayList<String>()
        var klasorAdlari=ArrayList<String>()

        var root:String  = Environment.getExternalStorageDirectory().path
        Log.e("HATA",root)

        var kameraResimleri:String = root+"/DCIM/Camera"
        var indirilenResimler:String  = root+"/Download"
        var whatsappResimleri:String  = root+"/WhatsApp/Media/WhatsApp Images"

        klasorPaths.add(kameraResimleri)
        klasorPaths.add(indirilenResimler)
        klasorPaths.add(whatsappResimleri)

        klasorAdlari.add("Kamera")
        klasorAdlari.add("İndirilenler")
        klasorAdlari.add("Whatsapp")

        var spinnerArrayAdapter:ArrayAdapter<String> = ArrayAdapter(activity,android.R.layout.simple_spinner_item, klasorAdlari)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        view.spnKalsorAdlari.adapter=spinnerArrayAdapter

        view.spnKalsorAdlari.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                var klasordekiDosyalar : ArrayList<String> = DosyaIslemleri.klasordekiDosyalariGetir(klasorPaths.get(position))

                var gridAdapter= ShareActivityGridViewAdapter(activity!!,R.layout.tek_sutun_grid_resim,klasordekiDosyalar)

                gridResimler.adapter=gridAdapter

                /*for(str:String in klasordekiDosyalar){
                    Log.e("HATA",str)
                }*/
            }

        }


        return view
    }


}
