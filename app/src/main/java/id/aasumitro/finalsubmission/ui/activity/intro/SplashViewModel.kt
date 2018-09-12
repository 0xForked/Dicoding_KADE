package id.aasumitro.finalsubmission.ui.activity.intro

import android.arch.lifecycle.ViewModel
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.KEY_FIRST_OPEN
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.KEY_LEAGUE_ALT_NAME
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.KEY_LEAGUE_ID
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.KEY_LEAGUE_NAME
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.STATUS_EVER
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.STATUS_NEVER
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashViewModel : ViewModel() {

    private var mNavigator: SplashNavigator? = null
    private var mRepository: Repository? = null

    fun initVM(navigator: SplashNavigator,
               repository: Repository) {
        this.mNavigator = navigator
        this.mRepository = repository
    }

    fun startTask() {
        val isFirst = mRepository?.getPrefs(KEY_FIRST_OPEN)
        when (isFirst) {
            null -> synchronizeData()
            STATUS_NEVER -> synchronizeData()
            STATUS_EVER -> validateData()
            else -> {
                mNavigator?.toast("Failed initializing task!")
                launchMainActivity()
            }
        }
    }

    private fun synchronizeData() {
        initLeagueData()
        mNavigator?.loadingStatus("Connecting to server . . .")
        mRepository.let { it ->
            it?.getRemoteTeams(leagueName())
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ onSuccess ->
                        mNavigator?.loadingStatus("Fetching data from server . . .")
                        val mRemoteData =
                                onSuccess.teamList as ArrayList<Team>
                        it.storeTeams(mRemoteData)
                        mRepository?.setPrefs(KEY_FIRST_OPEN, STATUS_EVER)
                        launchMainActivity()
                    }, { onError ->
                        onError.printStackTrace()
                        mNavigator?.toast("Failed fetching data from server!")
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
                    val data = mRepository?.getTeamCountByLeague(leagueName())
                    if (data == 0) {
                        synchronizeData()
                    } else {
                        launchMainActivity()
                    }
                }
    }

    private fun initLeagueData() {
        val leagueObject = mRepository?.getLeague()
        val leagueArray = leagueObject?.get("leagues")?.asJsonArray
        val leagueList = leagueArray?.get(0)?.asJsonObject
        val id = leagueList?.get("idLeague")
        val name = leagueList?.get("strLeague")
        val altName = leagueList?.get("strLeagueAlternate")
        mRepository?.setPrefs(KEY_LEAGUE_ID,
                id.toString().replace("\"", ""))
        mRepository?.setPrefs(KEY_LEAGUE_NAME,
                name.toString().replace("\"", ""))
        mRepository?.setPrefs(KEY_LEAGUE_ALT_NAME,
                altName.toString().replace("\"", ""))
    }

    private fun leagueName(): String? = mRepository?.getPrefs(KEY_LEAGUE_NAME)

    private fun launchMainActivity() = mNavigator?.startMain()

}