package id.aasumitro.submission003.ui.activity.splash

interface SplashNavigation {

    fun startMainActivity()

    fun loadingStatus(status: String?)

    fun makeToast(message: String?)

}