package id.aasumitro.submission004.ui.activity.detail

import android.widget.Toast

interface DetailNavigator {

    fun onSuccess(isSuccess: Boolean, code: String?, message: String?)

    fun onLoading(isLoading: Boolean)

    fun checkNetworkConnection(): Boolean?

    fun onNextMatch()

    fun showToast(message: String?): Toast

}