package id.aasumitro.finalsubmission.ui.activity.detail.team

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import id.aasumitro.finalsubmission.ui.fragment.overview.TeamOverviewFragment
import id.aasumitro.finalsubmission.ui.fragment.player.PlayerFragment

class TeamViewPagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> TeamOverviewFragment()
            1 -> PlayerFragment()
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Overview"
            1 -> "Players"
            else -> null
        }
    }

    override fun getCount(): Int = 2

}