package id.aasumitro.submission003.ui.fragment

import android.arch.lifecycle.ViewModel
import android.content.Context
import id.aasumitro.submission003.data.model.Favorite
import id.aasumitro.submission003.data.model.Match
import id.aasumitro.submission003.data.source.remote.ApiClient
import id.aasumitro.submission003.util.AppConst
import id.aasumitro.submission003.util.AppConst.ERROR_CODE_NETWORK_NOTAVAILABLE
import id.aasumitro.submission003.util.AppConst.ERROR_MESSAGE_NETWORK_NOTAVAILABLE
import id.aasumitro.submission003.util.AppConst.FAVORITE_MATCH
import id.aasumitro.submission003.util.AppConst.GLOBAL_MATCH_VALUE
import id.aasumitro.submission003.util.AppConst.NEXT_MATCH
import id.aasumitro.submission003.util.AppConst.PREV_MATCH
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE
import id.aasumitro.submission003.util.ankoDB
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import kotlin.collections.ArrayList


class MainViewModel : ViewModel() {

    private var mNavigator: MainNavigator? = null
    private var mApiClient: ApiClient? = null

    var mEventsData = ArrayList<Match>()
    var mFavoriteData = ArrayList<Favorite>()

    fun initVM(navigator: MainNavigator,
               apiClient: ApiClient) {
        this.mNavigator = navigator
        this.mApiClient = apiClient
    }

    fun startTask(isMatch: String?, leagueId: Int?, context: Context) {
        val isNetworkAvailable = mNavigator?.checkNetworkConnection()
        if (isNetworkAvailable as Boolean) {
            GLOBAL_MATCH_VALUE = isMatch
            when (isMatch) {
                PREV_MATCH -> getLastMatch(leagueId)
                NEXT_MATCH -> getNextMatch(leagueId)
                FAVORITE_MATCH -> getFavoriteMatch(context)
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

    private fun getLastMatch(leagueId: Int?) {
        mNavigator?.onLoading(true)
        mApiClient
                ?.apiServices()
                ?.lastMatch(leagueId)
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ onSuccess ->
                    mEventsData = onSuccess.eventList as ArrayList<Match>
                    mNavigator?.initListAdapter().let {
                        mNavigator?.onLoading(false)
                    }
                }, { onError ->
                    onError.printStackTrace()
                    onError.let {
                        mNavigator?.onLoading(false)
                        mNavigator?.onSuccess(
                                false,
                                AppConst.ERROR_CODE_UNKNOWN_ERROR,
                                AppConst.ERROR_MESSAGE_UNKNOWN_ERROR)
                    }
                })
    }

    private fun getNextMatch(leagueId: Int?) {
        mNavigator?.onLoading(true)
        mApiClient
                ?.apiServices()
                ?.nextMatch(leagueId)
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ onSuccess ->
                    mEventsData = onSuccess.eventList as ArrayList<Match>
                    mNavigator?.initListAdapter().let {
                        mNavigator?.onLoading(false)
                    }
                }, { onError ->
                    onError.printStackTrace()
                    onError.let {
                        mNavigator?.onLoading(false)
                        mNavigator?.onSuccess(
                                false,
                                AppConst.ERROR_CODE_UNKNOWN_ERROR,
                                AppConst.ERROR_MESSAGE_UNKNOWN_ERROR)
                    }
                })
    }

    private fun getFavoriteMatch(context: Context) {
        mNavigator?.onLoading(true)
        context.ankoDB.use {
            val result = select(TABLE_FAVORITE)
            val data = result.parseList(classParser<Favorite>())
                    as ArrayList<Favorite>
            mFavoriteData = data
            mNavigator?.initListAdapter().let {
                mNavigator?.onLoading(false)
            }
        }
    }

}