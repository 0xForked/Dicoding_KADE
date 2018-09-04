package id.aasumitro.submission004.ui.activity.splash

interface SplashNavigator {

    fun startMainActivity()

    fun loadingStatus(status: String?)

    fun makeToast(message: String?)

}