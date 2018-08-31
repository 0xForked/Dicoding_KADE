package id.aasumitro.submission002.ui.splash

interface SplashNavigation {

    fun startMainActivity()

    fun loadingStatus(status: String?)

    fun makeToast(message: String?)

}