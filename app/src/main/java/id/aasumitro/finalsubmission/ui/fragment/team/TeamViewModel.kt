package id.aasumitro.finalsubmission.ui.fragment.team

import android.arch.lifecycle.ViewModel
import android.util.Log
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_CODE_EMPTY_VALUE
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_CODE_UNKNOWN_ERROR
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_MESSAGE_EMPTY_FAVORITE_LIST
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_MESSAGE_UNKNOWN_ERROR
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TeamViewModel : ViewModel() {

    private var mNavigator: TeamNavigator? = null
    private var mRepository: Repository? = null

    var mTeamData = ArrayList<Team>()

    fun initVM(navigator: TeamNavigator,
               repository: Repository) {
        this.mNavigator = navigator
        this.mRepository = repository
    }

    fun startTask(leagueName: String) {
        mNavigator?.onLoading(true)
        mRepository.let { it ->
            it?.getLocalTeams(leagueName)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ onSuccess ->
                        mTeamData = onSuccess as ArrayList<Team>
                        mNavigator?.initListAdapter().let {
                            mNavigator?.onLoading(false)
                        }
                        if (mTeamData.size == 0) {
                            mNavigator?.onSuccess(
                                    false,
                                    ERROR_CODE_EMPTY_VALUE,
                                    ERROR_MESSAGE_EMPTY_FAVORITE_LIST)
                        }
                        mNavigator?.toast("success init list")
                        Log.d("TeamData", mTeamData.toString())
                    }, { onError ->
                        onError.printStackTrace()
                        mNavigator?.onLoading(false)
                        mNavigator?.onSuccess(
                                false,
                                ERROR_CODE_UNKNOWN_ERROR,
                                ERROR_MESSAGE_UNKNOWN_ERROR)
                    })
        }
    }

}