package id.aasumitro.submission003.ui.activity.detail

interface DetailNavigator {

    fun onSuccess(isSuccess: Boolean, code: String?, message: String?)

    fun onLoading(isLoading: Boolean)

    fun checkNetworkConnection(): Boolean?

    fun onNextMatch()

}