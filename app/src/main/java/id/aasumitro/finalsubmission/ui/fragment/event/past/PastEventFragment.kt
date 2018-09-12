package id.aasumitro.finalsubmission.ui.fragment.event.past

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
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
import id.aasumitro.finalsubmission.ui.activity.detail.match.MatchDetailActivity
import id.aasumitro.finalsubmission.ui.activity.detail.match.MatchDetailActivity.Companion.INTENT_DATA_EVENT_ID
import id.aasumitro.finalsubmission.ui.activity.detail.match.MatchDetailActivity.Companion.INTENT_MATCH_TYPE
import id.aasumitro.finalsubmission.ui.rv.listener.MatchListener
import id.aasumitro.finalsubmission.util.GlobalConst.GLOBAL_MATCH_VALUE
import id.aasumitro.finalsubmission.util.GlobalConst.PAST_MATCH
import id.aasumitro.finalsubmission.util.NetworkHelper.isNetworkAvailable
import kotlinx.android.synthetic.main.fragment_past_match.*
import org.jetbrains.anko.toast
import id.aasumitro.finalsubmission.util.gone
import id.aasumitro.finalsubmission.util.visible
import kotlinx.android.synthetic.main.item_error_layout.*
import org.jetbrains.anko.startActivity
import id.aasumitro.finalsubmission.ui.rv.adapter.MatchAdapter

class PastEventFragment : Fragment(), MatchListener, PastEventNavigator {

    private var mRepository: Repository? = null

    private val mViewModel: PastEventViewModel by lazy {
        ViewModelProviders
                .of(activity as FragmentActivity)
                .get(PastEventViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_past_match, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mRepository = Repository(activity)
        savedInstanceState.let {
            mViewModel.initVM(this, mRepository as Repository)
            mViewModel.getPastMatch()
        }
        initEventList()
        initRefreshLayout()
    }

    override fun checkNetworkConnection(): Boolean = isNetworkAvailable(activity as Context)

    override fun showToast(message: String) = activity?.toast(message)

    override fun onSuccess(isSuccess: Boolean, code: String?, message: String?) {
        if (isSuccess) {
            fr_past_match_error_layout.gone()
        } else {
            fr_past_match_error_layout.visible()
            item_error_txt_code.text = code
            item_error_txt_message.text = message
        }
    }

    override fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            fr_past_match_loading_layout.visible()
            fr_past_match_error_layout.gone()
        } else {
            fr_past_match_loading_layout.gone()
        }
    }

    private fun initRefreshLayout() {
        fr_past_match_refresh_layout.setOnRefreshListener {
            fr_past_match_refresh_layout.isRefreshing = false
            mViewModel.getPastMatch()
        }
    }

    private fun initEventList() {
        fr_past_match_rv.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager =
                LinearLayoutManager(activity)
        fr_past_match_rv.layoutManager = layoutManager
        fr_past_match_rv.itemAnimator = DefaultItemAnimator()
    }

    override fun initListAdapter() {
        fr_past_match_rv.adapter =
                MatchAdapter(
                        mViewModel.mPastMatch,
                        this,
                        PAST_MATCH)
    }
    override fun onMatchPressed(match: Match?) {
        GLOBAL_MATCH_VALUE = PAST_MATCH
        activity?.startActivity<MatchDetailActivity>(
                INTENT_DATA_EVENT_ID to match?.uniqueId,
                INTENT_MATCH_TYPE to PAST_MATCH)
    }

    override fun clearRecyclerView() {
        fr_past_match_rv.recycledViewPool.clear()
    }

}