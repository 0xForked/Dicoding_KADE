package id.aasumitro.finalsubmission.ui.fragment.event

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import id.aasumitro.finalsubmission.ui.fragment.event.next.NextEventFragment
import id.aasumitro.finalsubmission.ui.fragment.event.past.PastEventFragment

class EventViewPagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> NextEventFragment()
            1 -> PastEventFragment()
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Next"
            1 -> "Last"
            else -> null
        }
    }

    override fun getCount(): Int = 2

}