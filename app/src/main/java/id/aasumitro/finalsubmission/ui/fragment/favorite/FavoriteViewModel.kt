package id.aasumitro.finalsubmission.ui.fragment.favorite

import android.arch.lifecycle.ViewModel
import id.aasumitro.finalsubmission.data.model.Match
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_CODE_UNKNOWN_ERROR
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_MESSAGE_UNKNOWN_ERROR
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FavoriteViewModel : ViewModel() {

    private var mNavigator: FavoriteNavigator? = null
    private var mRepository: Repository? = null

    var mTeamData = ArrayList<Team>()
    var mMatchData = ArrayList<Match>()

    fun initVM(navigator: FavoriteNavigator,
                       repository: Repository) {
        this.mNavigator = navigator
        this.mRepository = repository
    }

    fun getFavoriteTeam() {
        mNavigator?.onLoading(true)
        mRepository.let { it ->
            it?.getFavoriteTeams()
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ onSuccess ->
                        mTeamData = onSuccess as ArrayList<Team>
                        mNavigator?.initTeamListAdapter().let {
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

    }

    fun getFavoriteMatch() {
        mNavigator?.onLoading(true)
        mRepository.let { it ->
            it?.getFavoriteMatch()
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ onSuccess ->
                        mMatchData = onSuccess as ArrayList<Match>
                        mNavigator?.initMatchListAdapter().let {
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

    }

}