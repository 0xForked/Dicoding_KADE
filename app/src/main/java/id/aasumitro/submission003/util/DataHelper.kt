package id.aasumitro.submission003.util

import android.content.Context
import id.aasumitro.submission003.data.model.Results
import id.aasumitro.submission003.data.source.prefs.SharedPreferences.defaultPrefs
import id.aasumitro.submission003.data.source.prefs.SharedPreferences.get
import id.aasumitro.submission003.data.source.prefs.SharedPreferences.set
import id.aasumitro.submission003.util.AppConst.FIRST_OPEN_STATUS
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_BANDAGE
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_STADIUM
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

object DataHelper {

    private var bandage: String? = null
    private var stadium: String? = null

    fun isFirstLaunch (context: Context) : String? {
        val mPrefs = defaultPrefs(context.applicationContext)
        return mPrefs[FIRST_OPEN_STATUS]
    }

    fun setFirstLaunchStatus (context: Context, value: String) {
        val mPrefs = defaultPrefs(context.applicationContext)
        mPrefs[FIRST_OPEN_STATUS] = value
    }

    fun getTeamBandage(context: Context?, uid: String?): String? {
        //return DBCreator
        //        .database(context!!)
        //        .teamDAO()
        //        .getTeamBandage(uid)

        context?.ankoDB!!.use {
            val result = select(TABLE_TEAMS, TABLE_TEAMS_COLUMN_BANDAGE)
                    .whereArgs("(uid = {id})",
                    "id" to uid!!)
            val data = result.parseSingle(classParser<Results.BandageData>())
            bandage = data.bandage
        }
        return bandage
    }

    fun getTeamStadium(context: Context?, uid: String?): String? {
        //return DBCreator
        //        .database(context!!)
        //        .teamDAO()
        //        .getTeamStadium(uid)

        context?.ankoDB!!.use {
            val result = select(TABLE_TEAMS, TABLE_TEAMS_COLUMN_STADIUM)
                    .whereArgs("(uid = {id})",
                    "id" to uid!!)
            val data = result.parseSingle(classParser<Results.StadiumData>())
            stadium = data.stadium
        }
        return stadium
    }

}