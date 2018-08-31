package id.aasumitro.submission002.ui.detail

interface DetailNavigator {

    fun onSuccess(isSuccess: Boolean, code: String?, message: String?)

    fun onLoading(isLoading: Boolean)

    fun checkNetworkConnection(): Boolean?

}