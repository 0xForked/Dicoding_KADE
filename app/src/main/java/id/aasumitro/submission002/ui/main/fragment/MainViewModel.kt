package id.aasumitro.submission002.ui.main.fragment

import android.arch.lifecycle.ViewModel
import id.aasumitro.submission002.data.model.Match
import id.aasumitro.submission002.data.remote.ApiClient
import id.aasumitro.submission002.util.AppConst.ERROR_CODE_EVENT_NULL
import id.aasumitro.submission002.util.AppConst.ERROR_CODE_NETWORK_NOTAVAILABLE
import id.aasumitro.submission002.util.AppConst.ERROR_MESSAGE_EVENT_NULL
import id.aasumitro.submission002.util.AppConst.ERROR_MESSAGE_NETWORK_NOTAVAILABLE
import id.aasumitro.submission002.util.AppConst.GLOBAL_MATCH_VALUE
import id.aasumitro.submission002.util.AppConst.NEXT_MATCH
import id.aasumitro.submission002.util.AppConst.PREV_MATCH
import id.aasumitro.submission002.util.AppConst.TODAY_MATCH
import id.aasumitro.submission002.util.DateTime
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class MainViewModel : ViewModel() {

    private var mNavigator: MainNavigator? = null
    private var mApiClient: ApiClient? = null
    var mEventsData = ArrayList<Match>()

    fun initVM(navigator: MainNavigator,
               apiClient: ApiClient) {
        this.mNavigator = navigator
        this.mApiClient = apiClient
    }

    fun startTask(isMatch: String, leagueId: Int, league: String?) {
        val isNetworkAvailable = mNavigator?.checkNetworkConnection()
        if (isNetworkAvailable!!) {
            GLOBAL_MATCH_VALUE = isMatch
            when (isMatch) {
                PREV_MATCH -> getLastMatch(leagueId)
                NEXT_MATCH -> getNextMatch(leagueId)
                TODAY_MATCH -> getMatchToday(league)
                else -> mNavigator?.showToast("Event list not available")
            }
        } else {
            mNavigator?.onSuccess(
                    false,
                    ERROR_CODE_NETWORK_NOTAVAILABLE,
                    ERROR_MESSAGE_NETWORK_NOTAVAILABLE)
            mNavigator?.onLoading(false)
        }

    }

    private fun getLastMatch(leagueId: Int) {
        mNavigator?.onLoading(true)
        mApiClient!!.apiServices()
                .lastMatch(leagueId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onSuccess ->
                    mEventsData = onSuccess.eventList as ArrayList<Match>
                    mNavigator?.initListAdapter().let {
                        mNavigator?.onLoading(false)
                    }
                }, { onError ->
                    onError.printStackTrace()
                    onError.let {
                        mNavigator?.onLoading(false)
                    }
                })
    }

    private fun getNextMatch(leagueId: Int) {
        mNavigator?.onLoading(true)
        mApiClient!!.apiServices()
                .nextMatch(leagueId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onSuccess ->
                    mEventsData = onSuccess.eventList as ArrayList<Match>
                    mNavigator?.initListAdapter().let {
                        mNavigator?.onLoading(false)
                    }
                }, { onError ->
                    onError.printStackTrace()
                    onError.let {
                        mNavigator?.onLoading(false)
                    }
                })
    }

    private fun getMatchToday(league: String?) {
        mNavigator?.onLoading(true)
        mApiClient!!.apiServices()
                .todayMatch(DateTime().thisTime(), league!!)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onSuccess ->
                     if (onSuccess.eventList === null) {
                        mNavigator?.onSuccess(
                                false,
                                ERROR_CODE_EVENT_NULL,
                                ERROR_MESSAGE_EVENT_NULL)
                        mNavigator?.onLoading(false)
                     } else {
                        mEventsData = onSuccess.eventList as ArrayList<Match>
                        mNavigator?.initListAdapter().let {
                            mNavigator?.onLoading(false)
                        }
                     }

                    // onSuccess?.let {
                    //    mEventsData = onSuccess.eventList as ArrayList<Match>
                    //    mNavigator?.initListAdapter().apply {
                    //        mNavigator?.onLoading(false)
                    //    }
                    // }

                }, { onError ->
                    onError.printStackTrace()
                    mNavigator?.onSuccess(
                            false,
                            ERROR_CODE_EVENT_NULL,
                            ERROR_MESSAGE_EVENT_NULL)
                    mNavigator?.onLoading(false)
                })
    }

}