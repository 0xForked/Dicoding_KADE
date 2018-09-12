package id.aasumitro.finalsubmission.ui.fragment.event.next

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.data.model.Match
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.ui.rv.listener.MatchListener
import id.aasumitro.finalsubmission.util.DateTimeHelper.getDay
import id.aasumitro.finalsubmission.util.DateTimeHelper.getHour
import id.aasumitro.finalsubmission.util.DateTimeHelper.getMinute
import id.aasumitro.finalsubmission.util.DateTimeHelper.getMonth
import id.aasumitro.finalsubmission.util.DateTimeHelper.getYear
import id.aasumitro.finalsubmission.util.NetworkHelper.isNetworkAvailable
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.toast
import id.aasumitro.finalsubmission.util.gone
import id.aasumitro.finalsubmission.util.visible
import kotlinx.android.synthetic.main.item_error_layout.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.util.*
import id.aasumitro.finalsubmission.ui.rv.adapter.MatchAdapter
import id.aasumitro.finalsubmission.util.GlobalConst.NEXT_MATCH

class NextEventFragment : Fragment(), MatchListener, NextEventNavigator {

    private var mRepository: Repository? = null

    private val mViewModel: NextEventViewModel by lazy {
        ViewModelProviders
                .of(activity as FragmentActivity)
                .get(NextEventViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_next_match, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mRepository = Repository(activity)
        savedInstanceState.let {
            mViewModel.initVM(this, mRepository as Repository)
            mViewModel.getNextMatch()
        }
        initEventList()
        initRefreshLayout()
    }

    override fun checkNetworkConnection(): Boolean = isNetworkAvailable(activity as Context)

    override fun showToast(message: String) = activity?.toast(message)

    override fun onSuccess(isSuccess: Boolean, code: String?, message: String?) {
        if (isSuccess) {
            fr_next_match_error_layout.gone()
        } else {
            fr_next_match_error_layout.visible()
            item_error_txt_code.text = code
            item_error_txt_message.text = message
        }
    }

    override fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            fr_next_match_loading_layout.visible()
            fr_next_match_error_layout.gone()
        } else {
            fr_next_match_loading_layout.gone()
        }
    }

    private fun initRefreshLayout() {
        fr_next_match_refresh_layout.setOnRefreshListener {
            fr_next_match_refresh_layout.isRefreshing = false
            mViewModel.getNextMatch()
        }
    }

    private fun initEventList() {
        fr_next_match_rv.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager =
                LinearLayoutManager(activity)
        fr_next_match_rv.layoutManager = layoutManager
        fr_next_match_rv.itemAnimator = DefaultItemAnimator()
    }

    override fun initListAdapter() {
        fr_next_match_rv.adapter =
                MatchAdapter(
                        mViewModel.mNextMatch,
                        this,
                        NEXT_MATCH)
    }

    override fun onMatchPressed(match: Match?) {
        activity?.alert("", "Remind me!") {
            yesButton {
                remindMatch(match)
            }
        }?.show()
    }

    override fun clearRecyclerView() {
        fr_next_match_rv.recycledViewPool.clear()
    }

    private fun remindMatch(match: Match?) {
        // guides https://s.id/2bvlw
        val description = "${match?.homeName} vs ${match?.awayName}"
        val year = getYear(match?.date)?.toInt() as Int
        val month = getMonth(match?.date)?.toInt() as Int
        val day = getDay(match?.date)?.toInt() as Int
        val hour = getHour(match?.time)?.toInt() as Int
        val minute = getMinute(match?.time)?.toInt() as Int
        val beginTime = Calendar.getInstance()
        beginTime.set(year, month, day, hour, minute)
        val endTime = Calendar.getInstance()
        endTime.set(year, month, day, hour, minute)

        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra(CalendarContract.Events.TITLE, description)
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
        intent.putExtra(CalendarContract.CalendarAlerts.ALARM_TIME, beginTime.timeInMillis)
        startActivity(intent)
    }
}