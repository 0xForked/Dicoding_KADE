package id.aasumitro.submission004.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.aasumitro.submission004.R
import id.aasumitro.submission004.data.models.Favorite
import id.aasumitro.submission004.data.models.Match
import id.aasumitro.submission004.data.sources.EventRepository
import id.aasumitro.submission004.ui.activity.detail.DetailActivity
import id.aasumitro.submission004.ui.fragment.rv.favorite.FavoriteListener
import id.aasumitro.submission004.ui.fragment.rv.event.EventListener
import id.aasumitro.submission004.util.AppConst.FAVORITE_MATCH
import id.aasumitro.submission004.util.AppConst.GLOBAL_MATCH_VALUE
import id.aasumitro.submission004.util.AppConst.INTENT_DATA_EVENT_ID
import id.aasumitro.submission004.util.AppConst.MATCH_VALUE
import id.aasumitro.submission004.util.NetworkHelper.isNetworkAvailable
import id.aasumitro.submission004.util.gone
import id.aasumitro.submission004.util.visible
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.item_error_layout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import id.aasumitro.submission004.data.sources.remote.ApiClient
import id.aasumitro.submission004.util.AppConst.LEAGUE_ID
import id.aasumitro.submission004.ui.fragment.rv.favorite.FavoriteAdapter
import id.aasumitro.submission004.ui.fragment.rv.event.EventAdapter

class ListFragment : Fragment(), ListNavigator, FavoriteListener, EventListener {

    private val mApiClient = ApiClient()
    private var mRepository: EventRepository? = null
    private var mEvent: String? = null

    private val mViewModel: ListViewModel by lazy {
        ViewModelProviders.of(activity!!).get(ListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle = this.arguments
        mEvent = bundle?.getString(MATCH_VALUE)
        mRepository = EventRepository(activity, mApiClient)
        savedInstanceState.let {
            mViewModel.initVM(this, mRepository as EventRepository)
            mViewModel.startTask(mEvent, LEAGUE_ID)
        }
        initEventList()
        initRefreshLayout()
    }

    override fun checkNetworkConnection(): Boolean = isNetworkAvailable(activity as Context)

    override fun showToast(message: String) = activity?.toast(message)

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
            mViewModel.startTask(mEvent, LEAGUE_ID)
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
                    FavoriteAdapter(
                            mViewModel.mFavoriteData,
                            this)
        } else {
            fr_main_rv.adapter =
                    EventAdapter(
                            mViewModel.mEventsData,
                            this)
        }
    }

    override fun clearRecyclerView() {
        fr_main_rv.recycledViewPool.clear()
    }

    override fun onEventPressed(match: Match?) {
        activity?.startActivity<DetailActivity>(
                INTENT_DATA_EVENT_ID to match?.eventId)
    }

    override fun onFavoritePressed(favorite: Favorite?) {
        activity?.startActivity<DetailActivity>(
                INTENT_DATA_EVENT_ID to favorite?.eventId)
    }

}
