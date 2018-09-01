package id.aasumitro.submission003.ui.activity.splash

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import id.aasumitro.submission003.data.model.Results
import id.aasumitro.submission003.data.model.Team
import id.aasumitro.submission003.data.source.remote.ApiClient
import id.aasumitro.submission003.util.AppConst.LEAGUE
import id.aasumitro.submission003.util.AppConst.STATUS_EVER
import id.aasumitro.submission003.util.AppConst.STATUS_NEVER
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_BANDAGE
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_NAME
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_NAME_SHORT
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_STADIUM
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_UID
import id.aasumitro.submission003.util.ankoDB
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.util.ArrayList

class SplashViewModel : ViewModel() {

    private var mNavigation: SplashNavigation? = null
    private var mApiClient: ApiClient? = null

    fun initVM(navigation: SplashNavigation,
               apiClient: ApiClient) {
        this.mNavigation = navigation
        this.mApiClient = apiClient
    }

    fun startTask(isFirstLaunch: String?, context: Context) {
        when (isFirstLaunch) {
            null -> {
                localDBTask(context)
                fetchFromServer(context)
            }
            STATUS_NEVER -> {
                localDBTask(context)
                fetchFromServer(context)
            }
            STATUS_EVER -> validateLocalData(context)
            else -> {
                mNavigation?.makeToast("Failed initializing task!")
                moveToMainActivity()
            }
        }
    }

    private fun localDBTask(context: Context) {
        mNavigation?.loadingStatus("Initializing local database . . .")
        //DBCreator.database(context)
    }

    private fun fetchFromServer(context: Context) {
        mNavigation?.loadingStatus("Connecting to server . . .")
        mApiClient
                ?.apiServices()
                ?.teamData(LEAGUE)
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ onSuccess ->
                    mNavigation?.loadingStatus("Fetching data from server . . .")
                    val mRemoteData =
                            onSuccess.teamList as ArrayList<Team>
                    insertLocalData(mRemoteData, context)
                }, { onError ->
                    onError.printStackTrace()
                    onError.let {
                        mNavigation?.makeToast("Failed fetching data from server!")
                        moveToMainActivity()
                    }
                })
    }

    private fun insertLocalData(teams: ArrayList<Team>, context: Context) {
        mNavigation?.loadingStatus("Inserting to local storage . . .")
        //DBCreator
        //        .database(context)
        //        .teamDAO()
        //        .insertTeams(teams)
        try {
            context.ankoDB.use {
                teams.forEach {
                    insert(TABLE_TEAMS,
                            TABLE_TEAMS_COLUMN_UID to it.uid,
                            TABLE_TEAMS_COLUMN_NAME to it.name,
                            TABLE_TEAMS_COLUMN_NAME_SHORT to it.shortName,
                            TABLE_TEAMS_COLUMN_BANDAGE to it.bandage,
                            TABLE_TEAMS_COLUMN_STADIUM to it.stadium
                    )
                }
            }
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
        }
        moveToMainActivity()
    }

    private fun validateLocalData(context: Context) {
        mNavigation?.loadingStatus("Validate local data . . .")
        // val teamCount = DBCreator
        //        .database(context)
        //        .teamDAO()
        //        .getDataCount()
        // if (teamCount == 0) {
        //     fetchFromServer(context)
        // } else {
        //     moveToMainActivity()
        // }

        try {
            context.ankoDB.use {
                val result = select(TABLE_TEAMS).column("count(uid)")
                val data = result.parseSingle(classParser<Results.CountData>())
                if (data.count == 0) fetchFromServer(context) else moveToMainActivity()
            }
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
        }

        // TODO Implement if teamDataLocal != teamDataServer

    }

    private fun moveToMainActivity() = mNavigation?.startMainActivity()

}