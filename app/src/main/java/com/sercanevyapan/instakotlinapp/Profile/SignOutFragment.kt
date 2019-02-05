package com.sercanevyapan.instakotlinapp.Profile



import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth


import com.sercanevyapan.instakotlinapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SignOutFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var alert = AlertDialog.Builder(this!!.activity!!)
            .setTitle("Program'dan çıkış yap")
            .setMessage("Emin misiniz?")
            .setPositiveButton("Çıkış yap",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    FirebaseAuth.getInstance().signOut()
                    activity!!.finish()
                }


            })
            .setNegativeButton("İptal", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dismiss()
                }
            }).create()

        return alert
    }


}
