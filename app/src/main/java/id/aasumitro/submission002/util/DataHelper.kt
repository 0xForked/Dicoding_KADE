package id.aasumitro.submission002.util

import android.content.Context
import id.aasumitro.submission002.data.local.DBCreator
import id.aasumitro.submission002.data.prefs.SharedPreferences.defaultPrefs
import id.aasumitro.submission002.data.prefs.SharedPreferences.get
import id.aasumitro.submission002.data.prefs.SharedPreferences.set
import id.aasumitro.submission002.util.AppConst.FIRST_OPEN_STATUS

object DataHelper {

    fun isFirstLaunch (context: Context) : String? {
        val mPrefs = defaultPrefs(context.applicationContext)
        return mPrefs[FIRST_OPEN_STATUS]
    }

    fun setFirstLaunchStatus (context: Context, value: String) {
        val mPrefs = defaultPrefs(context.applicationContext)
        mPrefs[FIRST_OPEN_STATUS] = value
    }

    // TODO (Repository Pattern)
    fun getTeamBandage(context: Context?, uid: String?): String {
        return DBCreator
                .database(context!!)
                .teamDAO()
                .getTeamBandage(uid)
    }

    fun getTeamStadium(context: Context?, uid: String?): String {
        return DBCreator
                .database(context!!)
                .teamDAO()
                .getTeamStadium(uid)
    }

}