package id.aasumitro.submission001.ui.fragment

import android.arch.lifecycle.ViewModel
import id.aasumitro.submission001.data.model.ResultData
import id.aasumitro.submission001.data.remote.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Agus Adhi Sumitro on 28/08/2018.
 * https://aasumitro.id
 * aasumitro@gmail.com
 */

class FMVIewModel : ViewModel() {

    private var mNavigation: FMNavigator? = null
    private var mApiClient: ApiClient? = null

    var newsList = ArrayList<ResultData>()

    fun initViewModel(navigator: FMNavigator, apiClient: ApiClient) {
        this.mNavigation = navigator
        this.mApiClient = apiClient
    }

    fun startTask(source: String, key: String) {
        mApiClient!!
                .apiServices()
                .everythingNews(source, key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ onSuccess ->
                    if (onSuccess.status === "error") {
                        //TODO
                    } else {
                        newsList = onSuccess.articles as ArrayList<ResultData>
                        mNavigation?.initAdapter().let {
                            //TODO
                        }
                    }
                }, { onError ->
                    onError.printStackTrace()
                    onError.let {
                        //TODO
                    }
                })
    }

}