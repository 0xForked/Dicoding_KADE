package id.aasumitro.submission004.ui.activity.splash

import android.arch.lifecycle.ViewModel
import android.util.Log
import id.aasumitro.submission004.data.models.Team
import id.aasumitro.submission004.data.sources.EventRepository
import id.aasumitro.submission004.util.AppConst.LEAGUE
import id.aasumitro.submission004.util.AppConst.STATUS_EVER
import id.aasumitro.submission004.util.AppConst.STATUS_NEVER
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashViewModel: ViewModel() {

    private var mNavigator: SplashNavigator? = null
    private var mRepository: EventRepository? = null

    fun initVM(navigator: SplashNavigator,
               repository: EventRepository) {
        this.mNavigator = navigator
        this.mRepository = repository
    }

    fun startTask() {
        val isFirst = mRepository?.isFirstLaunch()
        when (isFirst) {
            null -> synchronizeData()
            STATUS_NEVER -> synchronizeData()
            STATUS_EVER -> validateData()
            else -> {
                mNavigator?.makeToast("Failed initializing task!")
                launchMainActivity()
            }
        }
    }

    private fun synchronizeData() {
        mNavigator?.loadingStatus("Connecting to server . . .")
        mRepository.let { it ->
                it?.getTeamData(LEAGUE)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ onSuccess ->
                    mNavigator?.loadingStatus("Fetching data from server . . .")
                    val mRemoteData =
                            onSuccess.teamList as ArrayList<Team>
                    it.insertTeams(mRemoteData)
                    mRepository?.setFirstLaunchStatus(STATUS_EVER)
                    launchMainActivity()
                }, { onError ->
                    onError.printStackTrace()
                    mNavigator?.makeToast("Failed fetching data from server!")
                    launchMainActivity()
                })
        }

    }

    private fun validateData() {
        mNavigator?.loadingStatus("Validate local data . . .")
        Observable.interval(5, TimeUnit.SECONDS)
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val data = mRepository?.getDataCount()
                    if (data == 0) {
                        synchronizeData()
                    } else {
                        launchMainActivity()
                    }
                    Log.d("localTeamData",
                            "total team data $data")
                }

        // TODO Implement if teamDataLocal != teamDataServer

    }

    private fun launchMainActivity() = mNavigator?.startMainActivity()

}
