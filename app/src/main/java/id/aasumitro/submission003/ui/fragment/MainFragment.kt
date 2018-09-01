package id.aasumitro.submission003.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.aasumitro.submission003.R
import id.aasumitro.submission003.data.model.Favorite
import id.aasumitro.submission003.data.model.Match
import id.aasumitro.submission003.util.gone
import id.aasumitro.submission003.util.visible
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.item_error_layout.*
import id.aasumitro.submission003.data.source.remote.ApiClient
import id.aasumitro.submission003.ui.activity.detail.DetailActivity
import id.aasumitro.submission003.ui.dialog.NextMatchDetail
import id.aasumitro.submission003.ui.fragment.rv.favorite.FavoriteListener
import id.aasumitro.submission003.util.AppConst.LEAGUE_ID
import id.aasumitro.submission003.util.AppConst.MATCH_VALUE
import org.jetbrains.anko.toast
import id.aasumitro.submission003.ui.fragment.rv.main.MainListener
import id.aasumitro.submission003.util.AppConst.FAVORITE_MATCH
import id.aasumitro.submission003.util.AppConst.GLOBAL_MATCH_VALUE
import id.aasumitro.submission003.util.AppConst.INTENT_DATA_EVENT_ID
import id.aasumitro.submission003.util.AppConst.NEXT_MATCH
import id.aasumitro.submission003.util.NetworkHelper.isNetworkAvailable
import org.jetbrains.anko.startActivity
import id.aasumitro.submission003.ui.fragment.rv.main.MainAdapter
import id.aasumitro.submission003.ui.fragment.rv.favorite.FavoriteAdapter

class MainFragment : Fragment(), MainNavigator, MainListener, FavoriteListener {

    private val mApiClient = ApiClient()
    private var mEvent: String? = null

    private val mViewModel: MainViewModel by lazy {
        ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle = this.arguments
        mEvent = bundle?.getString(MATCH_VALUE)
        savedInstanceState.let {
            mViewModel.initVM(this, mApiClient)
            mViewModel.startTask(mEvent, LEAGUE_ID, activity!!)
        }
        initEventList()
        initRefreshLayout()
    }

    override fun showToast(message: String) {
        activity?.toast(message)
    }

    override fun onSuccess(isSuccess: Boolean, code: String?, message: String?) {
        if (isSuccess) {
            fr_main_refresh_layout.visible()
            fr_main_error_layout.gone()
        } else {
            fr_main_refresh_layout.gone()
            fr_main_error_layout.visible()
            item_error_txt_code.text = code
            item_error_txt_message.text = message
        }
    }

    override fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            fr_main_loading_layout.visible()
            fr_main_refresh_layout.gone()
            fr_main_error_layout.gone()
        } else {
            fr_main_loading_layout.gone()
            fr_main_refresh_layout.visible()
        }
    }

    private fun initRefreshLayout() {
        fr_main_refresh_layout.setOnRefreshListener {
            fr_main_refresh_layout.isRefreshing = false
            mViewModel.startTask(mEvent, LEAGUE_ID, activity!!)
        }
    }

    private fun initEventList() {
        fr_main_rv.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager =
                LinearLayoutManager(activity)
        fr_main_rv.layoutManager = layoutManager
        fr_main_rv.itemAnimator = DefaultItemAnimator()
    }

    override fun initListAdapter() {
        if (GLOBAL_MATCH_VALUE === FAVORITE_MATCH) {
            fr_main_rv.adapter =
                    FavoriteAdapter(mViewModel.mFavoriteData,
                            this)
        } else {
            fr_main_rv.adapter =
                    MainAdapter(mViewModel.mEventsData,
                            this)
        }
    }

    override fun clearRecyclerView() {
        fr_main_rv.recycledViewPool.clear()
    }

    override fun onMatchPressed(match: Match) {
        if (GLOBAL_MATCH_VALUE === NEXT_MATCH) {
            activity?.startActivity<NextMatchDetail>(
                    INTENT_DATA_EVENT_ID to match.eventId)
        } else {
            activity?.startActivity<DetailActivity>(
                    INTENT_DATA_EVENT_ID to match.eventId)
        }
    }

    override fun onFavoritePressed(favorite: Favorite?) {
        activity?.startActivity<DetailActivity>(
                INTENT_DATA_EVENT_ID to favorite?.eventId)
    }

    override fun checkNetworkConnection() : Boolean? {
        return isNetworkAvailable(context)
    }

}
