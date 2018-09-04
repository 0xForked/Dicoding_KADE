package id.aasumitro.submission004.ui.activity.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import id.aasumitro.submission004.data.models.Favorite
import id.aasumitro.submission004.data.models.Match
import id.aasumitro.submission004.data.sources.EventRepository
import id.aasumitro.submission004.data.sources.remote.ApiClient
import id.aasumitro.submission004.util.AppConst.ERROR_CODE_DATA_NOTAVAILABLE
import id.aasumitro.submission004.util.AppConst.ERROR_CODE_NETWORK_NOTAVAILABLE
import id.aasumitro.submission004.util.AppConst.ERROR_MESSAGE_DATA_NOTAVAILABLE
import id.aasumitro.submission004.util.AppConst.ERROR_MESSAGE_NETWORK_NOTAVAILABLE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailViewModel : ViewModel() {

    private var mNavigator: DetailNavigator? = null
    private var mRepository: EventRepository? = null
    private var mApiClient: ApiClient? = null

    private var mMatchDetail = MutableLiveData<Match>()

    private var mFavoriteEvent: Boolean = false

    fun initVM(navigator: DetailNavigator,
               repository: EventRepository,
               apiClient: ApiClient) {
        this.mNavigator = navigator
        this.mRepository = repository
        this.mApiClient = apiClient
    }

    fun startTask(eventId: String?) {
        val isNetworkAvailable =
                mNavigator?.checkNetworkConnection()
        if (isNetworkAvailable as Boolean) {
            detailEvent(eventId)
        } else {
            mNavigator?.onSuccess(
                    false,
                    ERROR_CODE_NETWORK_NOTAVAILABLE,
                    ERROR_MESSAGE_NETWORK_NOTAVAILABLE)
            mNavigator?.onLoading(false)
        }
    }

    private fun detailEvent(eventId: String?) {
        mNavigator?.onLoading(true)
        mApiClient?.apiServices()
                ?.detailMatch(eventId?.toInt())
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ onSuccess ->
                    val dataResult = onSuccess.eventList[0]
                    Log.d("DataResult", dataResult.toString())
                    mMatchDetail.value = dataResult
                    mNavigator?.onLoading(false)
                    mNavigator?.showToast("success get detail")
                }, { onError ->
                    onError.printStackTrace()
                    mNavigator?.onSuccess(
                        false,
                        ERROR_CODE_DATA_NOTAVAILABLE,
                        ERROR_MESSAGE_DATA_NOTAVAILABLE)
                    mNavigator?.onLoading(false)
                    mNavigator?.onNextMatch()
                    mNavigator?.showToast("success get detail")

                })
    }

    fun markAsFavorite(favorite: Favorite?) {
        Log.d("MarkFavorite", favorite.toString())
        mRepository?.insertFavorite(favorite)
        mNavigator?.showToast("mark as favorite")
    }

    fun removeFromFavorite(eventId: Int?) {
        mRepository?.deleteFavorite(eventId)
        mNavigator?.showToast("delete from favorite")
    }

    fun favoriteState(eventId: Int?) {
        val favorite = mRepository?.getFavoriteState(eventId)
        if (favorite !== null) {
            favorite.let {
                mFavoriteEvent = !it.isEmpty()
            }
        }
    }

    fun getDataResult(): LiveData<Match> = mMatchDetail

    fun getFavoriteState(): Boolean = mFavoriteEvent

}