package id.aasumitro.submission002.ui.splash

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import id.aasumitro.submission002.data.local.DBCreator
import id.aasumitro.submission002.data.model.Match
import id.aasumitro.submission002.data.model.Team
import id.aasumitro.submission002.data.remote.ApiClient
import id.aasumitro.submission002.util.AppConst.LEAGUE
import id.aasumitro.submission002.util.AppConst.STATUS_EVER
import id.aasumitro.submission002.util.AppConst.STATUS_NEVER
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class SplashViewModel : ViewModel() {

    // BAD IMPLEMENTATION
    // ACCESS DB IN MAIN THREAD
    // MAKE MANY REQUEST TO API
    // TODO (RESOLVE THE PROBLEM)
    // TODO (REPOSITORY PATTERN)

    private var mNavigation: SplashNavigation? = null
    private var mApiClient: ApiClient? = null

    fun initVM(navigation: SplashNavigation,
               apiClient: ApiClient) {
        this.mNavigation = navigation
        this.mApiClient = apiClient
    }

    fun startTask(isFirstLaunch: String?, context: Context?) {
        Log.d("FirstStatus", isFirstLaunch.toString())
        when (isFirstLaunch) {
            null -> {
                // TODO
                // in this case app will do 0 point
                localDBTask(context)
                fetchFromServer(context)
            }
            STATUS_NEVER -> {
                // TODO
                // 0 = false ( user first launch )
                // in this case app will create local db
                // after that app will access to server and get
                // teams data when teams data success fetched app will
                // save/insert all teams data needed in local storage
                localDBTask(context)
                fetchFromServer(context)
            }
            STATUS_EVER -> {
                // TODO
                // 1 = true ( user is not first launch the app )
                // in this case app will connect to server and save
                // team data to local when server data count != local data count
                // or local data count == 0
                validateLocalData(context)
            }
            else -> {
                mNavigation?.makeToast("Failed initializing task!")
                moveToMainActivity()
            }
        }
    }

    private fun localDBTask(context: Context?) {
        DBCreator.database(context!!)
        mNavigation?.loadingStatus("Initializing local database . . .")
    }

    private fun fetchFromServer(context: Context?) {
        mNavigation?.loadingStatus("Connecting to server . . .")
        mApiClient?.apiServices()!!.teamData(LEAGUE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onSuccess ->
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

    private fun insertLocalData(teams: ArrayList<Team>, context: Context?) {
        mNavigation?.loadingStatus("Inserting to local storage . . .")
        DBCreator
                .database(context!!)
                .teamDAO()
                .insetTeams(teams)
        moveToMainActivity()
    }

    private fun validateLocalData(context: Context?) {
        mNavigation?.loadingStatus("Validate local data . . .")
        val teamCount = DBCreator
                .database(context!!)
                .teamDAO()
                .getDataCount()
        if (teamCount == 0) {
            fetchFromServer(context)
        } else {
            moveToMainActivity()
        }

        // TODO Implement if teamCountLocal != teamCountServer

    }

    private fun moveToMainActivity() {
        mNavigation?.startMainActivity()
    }

}