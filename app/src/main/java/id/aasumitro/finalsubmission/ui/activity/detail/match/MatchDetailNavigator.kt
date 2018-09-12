package id.aasumitro.finalsubmission.ui.activity.detail.match

import android.widget.Toast

interface MatchDetailNavigator {

    fun makeToast(message: String?): Toast?

    fun onSuccess(isSuccess: Boolean, code: String?, message: String?)

    fun onLoading(isLoading: Boolean)

    fun checkNetworkConnection(): Boolean?

    fun onNextMatch()

}