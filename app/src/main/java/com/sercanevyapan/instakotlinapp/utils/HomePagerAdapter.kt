package com.sercanevyapan.instakotlinapp.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class HomePagerAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {

    private val mFragmentList:ArrayList<Fragment> = ArrayList()

    //fragment pager adapterin yazmayı zorunlu kıldığı fonksiyonlar
    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)
    }

    //fragment pager adapterin yazmayı zorunlu kıldığı fonksiyonlar
    override fun getCount(): Int {

        return mFragmentList.size
    }

    //kişisel fonksiyon
    fun addFragment(fragment: Fragment){
        mFragmentList.add(fragment)
    }
}