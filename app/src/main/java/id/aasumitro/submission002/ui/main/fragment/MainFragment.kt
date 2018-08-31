package id.aasumitro.submission002.ui.main.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.aasumitro.submission002.R
import id.aasumitro.submission002.data.model.Match
import id.aasumitro.submission002.util.gone
import id.aasumitro.submission002.util.visible
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.item_error_layout.*
import id.aasumitro.submission002.data.remote.ApiClient
import id.aasumitro.submission002.ui.detail.DetailActivity
import id.aasumitro.submission002.util.AppConst.LEAGUE
import id.aasumitro.submission002.util.AppConst.LEAGUE_ID
import id.aasumitro.submission002.util.AppConst.MATCH_VALUE
import org.jetbrains.anko.toast
import id.aasumitro.submission002.ui.main.fragment.rv.MainAdapter
import id.aasumitro.submission002.ui.main.fragment.rv.MainListener
import id.aasumitro.submission002.util.AppConst.INTENT_DATA_EVENT_ID
import id.aasumitro.submission002.util.NetworkHelper.isNetworkAvailable
import org.jetbrains.anko.startActivity

class MainFragment : Fragment(), MainNavigator, MainListener {

    private val mApiClient = ApiClient()
    private var mEvent: String? = null

    private val mViewModel: MainViewModel by lazy {
        ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle = this.arguments
        mEvent = bundle?.getString(MATCH_VALUE)
        savedInstanceState.let {
            mViewModel.initVM(this, mApiClient)
            mViewModel.startTask(mEvent!!, LEAGUE_ID, LEAGUE)
        }
        initEventList()
        initRefreshLayout()
    }

    override fun showToast(message: String) {
        activity?.toast(message)
    }

    override fun onSuccess(isSuccess: Boolean, code: String?, message: String?) {
        if (isSuccess) {
            swipeRefreshLayout.visible()
            layout_error.gone()
        } else {
            swipeRefreshLayout.gone()
            layout_error.visible()
            error_home_code.text = code
            error_home_message.text = message
        }
    }

    override fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            layout_loading.visible()
            swipeRefreshLayout.gone()
            layout_error.gone()
        } else {
            layout_loading.gone()
            swipeRefreshLayout.visible()
        }
    }

    private fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            mViewModel.startTask(mEvent!!, LEAGUE_ID, LEAGUE)
        }
    }

    private fun initEventList() {
        rv_event.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager =
                LinearLayoutManager(activity)
        rv_event.layoutManager = layoutManager
        rv_event.itemAnimator = DefaultItemAnimator()
    }

    override fun initListAdapter() {
        rv_event.adapter =
                MainAdapter(mViewModel.mEventsData,
                        this)
    }

    override fun clearRecyclerView() {
        rv_event.recycledViewPool.clear()
    }

    override fun onMatchPressed(match: Match) {
        activity?.startActivity<DetailActivity>(INTENT_DATA_EVENT_ID to match.eventId)
    }

    override fun checkNetworkConnection() : Boolean? {
        return isNetworkAvailable(context)
    }

}
