package id.aasumitro.finalsubmission.ui.fragment.event.past

import android.arch.lifecycle.ViewModel
import id.aasumitro.finalsubmission.data.model.Match
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.KEY_LEAGUE_ID
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_CODE_NETWORK_NOTAVAILABLE
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_CODE_UNKNOWN_ERROR
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_MESSAGE_NETWORK_NOTAVAILABLE
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_MESSAGE_UNKNOWN_ERROR
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PastEventViewModel : ViewModel() {

    private var mNavigator: PastEventNavigator? = null
    private var mRepository: Repository? = null

    var mPastMatch = ArrayList<Match>()

    fun initVM(navigator: PastEventNavigator,
               repository: Repository) {
        this.mNavigator = navigator
        this.mRepository = repository
    }

    fun getPastMatch() {
        mNavigator?.onLoading(true)
        mNavigator?.clearRecyclerView()
        val isNetworkAvailable: Boolean =
                mNavigator?.checkNetworkConnection() as Boolean
        if (isNetworkAvailable) {
            mRepository.let { it ->
                it?.getLastMatch(leagueId())
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe({ onSuccess ->
                            mPastMatch = onSuccess.matchList as ArrayList<Match>
                            mNavigator?.initListAdapter().let {
                                mNavigator?.onLoading(false)
                            }
                        }, { onError ->
                            onError.printStackTrace()
                            mNavigator?.onLoading(false)
                            mNavigator?.onSuccess(
                                    false,
                                    ERROR_CODE_UNKNOWN_ERROR,
                                    ERROR_MESSAGE_UNKNOWN_ERROR)
                        })
            }
        } else {
            mNavigator?.onSuccess(
                    false,
                    ERROR_CODE_NETWORK_NOTAVAILABLE,
                    ERROR_MESSAGE_NETWORK_NOTAVAILABLE)
            mNavigator?.onLoading(false)
        }

    }

    private fun leagueId(): Int? =
            mRepository?.getPrefs(KEY_LEAGUE_ID)?.toInt()

}