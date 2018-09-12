package id.aasumitro.finalsubmission.ui.activity.intro

import android.widget.Toast

interface SplashNavigator {

    fun startMain()

    fun loadingStatus(status: String?)

    fun toast(message: String?): Toast?

}