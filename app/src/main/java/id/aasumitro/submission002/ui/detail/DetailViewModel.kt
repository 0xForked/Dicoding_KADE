package id.aasumitro.submission002.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import id.aasumitro.submission002.data.model.Match
import id.aasumitro.submission002.data.remote.ApiClient
import id.aasumitro.submission002.util.AppConst.ERROR_CODE_NETWORK_NOTAVAILABLE
import id.aasumitro.submission002.util.AppConst.ERROR_CODE_UNKNOWN_ERROR
import id.aasumitro.submission002.util.AppConst.ERROR_MESSAGE_NETWORK_NOTAVAILABLE
import id.aasumitro.submission002.util.AppConst.ERROR_MESSAGE_UNKNOWN_ERROR
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailViewModel : ViewModel() {

    private var mNavigator: DetailNavigator? = null
    private var mApiClient: ApiClient? = null

    private var mMatchDetail = MutableLiveData<Match>()

    fun initVM(navigation: DetailNavigator,
               apiClient: ApiClient) {
        this.mNavigator = navigation
        this.mApiClient = apiClient
    }

    fun startTask(eventId: String) {
        val isNetworkAvailable = mNavigator?.checkNetworkConnection()
        if (isNetworkAvailable!!) {
            detailEvent(eventId)
        } else {
            mNavigator?.onSuccess(
                    false,
                    ERROR_CODE_NETWORK_NOTAVAILABLE,
                    ERROR_MESSAGE_NETWORK_NOTAVAILABLE)
            mNavigator?.onLoading(false)
        }

    }

    private fun detailEvent(eventId: String) {
        mNavigator?.onLoading(true)
        mApiClient?.apiServices()!!
                .matchDetail(eventId.toInt())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onSuccess ->
                    val dataResult = onSuccess.eventList[0]
                    mMatchDetail.value = dataResult
                    mNavigator?.onLoading(false)
                }, { onError ->
                    onError.printStackTrace()
                    onError.let {
                        mNavigator?.onSuccess(
                                false,
                                ERROR_CODE_UNKNOWN_ERROR,
                                ERROR_MESSAGE_UNKNOWN_ERROR)
                        mNavigator?.onLoading(false)
                    }
                })
    }

    fun getDataResult(): LiveData<Match> = mMatchDetail

}