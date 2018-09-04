package id.aasumitro.submission004.ui.fragment

import android.arch.lifecycle.ViewModel
import id.aasumitro.submission004.data.models.Favorite
import id.aasumitro.submission004.data.models.Match
import id.aasumitro.submission004.data.sources.EventRepository
import id.aasumitro.submission004.util.AppConst.ERROR_CODE_EMPTY_VALUE
import id.aasumitro.submission004.util.AppConst.ERROR_CODE_NETWORK_NOTAVAILABLE
import id.aasumitro.submission004.util.AppConst.ERROR_CODE_UNKNOWN_ERROR
import id.aasumitro.submission004.util.AppConst.ERROR_MESSAGE_EMPTY_FAVORITE_LIST
import id.aasumitro.submission004.util.AppConst.ERROR_MESSAGE_NETWORK_NOTAVAILABLE
import id.aasumitro.submission004.util.AppConst.ERROR_MESSAGE_UNKNOWN_ERROR
import id.aasumitro.submission004.util.AppConst.FAVORITE_MATCH
import id.aasumitro.submission004.util.AppConst.GLOBAL_MATCH_VALUE
import id.aasumitro.submission004.util.AppConst.NEXT_MATCH
import id.aasumitro.submission004.util.AppConst.PREV_MATCH
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel() {

    private var mNavigator: ListNavigator? = null
    private var mRepository: EventRepository? = null

    var mEventsData = ArrayList<Match>()
    var mFavoriteData = ArrayList<Favorite>()

    fun initVM(navigator: ListNavigator,
               repository: EventRepository) {
        this.mNavigator = navigator
        this.mRepository = repository
    }

    fun startTask(isMatch: String?, leagueId: Int?) {
        val isNetworkAvailable: Boolean = mNavigator?.checkNetworkConnection() as Boolean
        if (isNetworkAvailable) {
            GLOBAL_MATCH_VALUE = isMatch
            mNavigator?.onLoading(true)
            when (isMatch) {
                PREV_MATCH -> getLastMatch(leagueId)
                NEXT_MATCH -> getNextMatch(leagueId)
                FAVORITE_MATCH -> getFavoriteMatch()
                else -> {
                    mNavigator?.showToast("Event list not available")
                    mNavigator?.onLoading(false)
                }
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
        mRepository.let { it ->
            it?.getLastMatch(leagueId)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ onSuccess ->
                        mEventsData = onSuccess.eventList as ArrayList<Match>
                        mNavigator?.initListAdapter().let {
                            mNavigator?.onLoading(false)
                            mNavigator?.showToast("success init list")
                        }
                    }, { onError ->
                        onError.printStackTrace()
                        mNavigator?.onLoading(false)
                        mNavigator?.onSuccess(
                                false,
                                ERROR_CODE_UNKNOWN_ERROR,
                                ERROR_MESSAGE_UNKNOWN_ERROR)
                    })
        }
    }

    private fun getNextMatch(leagueId: Int?) {
        mRepository.let { it ->
            it?.getNextMatch(leagueId)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ onSuccess ->
                        mEventsData = onSuccess.eventList as ArrayList<Match>
                        mNavigator?.initListAdapter().let {
                            mNavigator?.onLoading(false)
                        }
                        mNavigator?.showToast("success init list")
                    }, { onError ->
                        onError.printStackTrace()
                        mNavigator?.onLoading(false)
                        mNavigator?.onSuccess(
                                false,
                                ERROR_CODE_UNKNOWN_ERROR,
                                ERROR_MESSAGE_UNKNOWN_ERROR)
                    })

        }
    }

    private fun getFavoriteMatch() {
        mRepository.let { it ->
            it?.getFavorite()
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ onSuccess ->
                        mFavoriteData = onSuccess as ArrayList<Favorite>
                        mNavigator?.initListAdapter().let {
                            mNavigator?.onLoading(false)
                        }
                        if (mFavoriteData.size == 0) {
                            mNavigator?.onSuccess(
                                    false,
                                    ERROR_CODE_EMPTY_VALUE,
                                    ERROR_MESSAGE_EMPTY_FAVORITE_LIST)
                        }
                        mNavigator?.showToast("success init list")

                    }, { onError ->
                        onError.printStackTrace()
                        mNavigator?.onLoading(false)
                        mNavigator?.onSuccess(
                                false,
                                ERROR_CODE_UNKNOWN_ERROR,
                                ERROR_MESSAGE_UNKNOWN_ERROR)
                    })
        }
    }

}