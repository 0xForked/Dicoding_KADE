package id.aasumitro.finalsubmission.ui.fragment.event

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.aasumitro.finalsubmission.R
import kotlinx.android.synthetic.main.fragment_event.*

class EventFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_event, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val fragmentAdapter =
                EventViewPagerAdapter(activity?.supportFragmentManager as FragmentManager)
        fr_event_view_pager.adapter = fragmentAdapter
        fr_event_tab_layout.setupWithViewPager(fr_event_view_pager)
    }

}