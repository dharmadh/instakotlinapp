package com.sercanevyapan.instakotlinapp.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.ArrayList

class SharePagerAdapter(fragmentManager: FragmentManager, tabAdlari:ArrayList<String>) : FragmentPagerAdapter(fragmentManager) {

    private var mFragmentList:ArrayList<Fragment> = ArrayList()
    private var mTabAdlari:ArrayList<String> = tabAdlari

    override fun getItem(p0: Int): Fragment {
            return mFragmentList.get(p0)
    }

    override fun getCount(): Int {
            return mFragmentList.size
    }

    fun addFragment(fragment: Fragment){
        mFragmentList.add(fragment)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTabAdlari.get(position)
    }

}