package id.aasumitro.finalsubmission.ui.activity.detail.match

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import id.aasumitro.finalsubmission.data.model.Match
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_CODE_DATA_NOTAVAILABLE
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_CODE_NETWORK_NOTAVAILABLE
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_MESSAGE_DATA_NOTAVAILABLE
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_MESSAGE_NETWORK_NOTAVAILABLE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MatchDetailViewModel : ViewModel() {

    private var mNavigator: MatchDetailNavigator? = null
    private var mRepository: Repository? = null

    private var mMatchDetail = MutableLiveData<Match>()

    private var mFavoriteEvent: Boolean = false

    fun initVM(navigator: MatchDetailNavigator,
               repository: Repository) {
        this.mNavigator = navigator
        this.mRepository = repository

    }

    fun startTask(eventId: String?) {
        val isNetworkAvailable =
                mNavigator?.checkNetworkConnection()
        if (isNetworkAvailable as Boolean) {
            mNavigator?.onLoading(true)
            detailEvent(eventId)
        } else {
            mNavigator?.onSuccess(
                    false,
                    ERROR_CODE_NETWORK_NOTAVAILABLE,
                    ERROR_MESSAGE_NETWORK_NOTAVAILABLE)
            mNavigator?.onLoading(false)
        }
    }


    private fun detailEvent(eventId: String?) {
        mRepository.let {
            it?.getMatchDetail(eventId?.toInt())
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ onSuccess ->
                        val dataResult = onSuccess.matchList[0]
                        Log.d("DataResult", dataResult.toString())
                        mMatchDetail.value = dataResult
                        mNavigator?.onLoading(false)
                    }, { onError ->
                        onError.printStackTrace()
//                        mNavigator?.onSuccess(
//                                false,
//                                ERROR_CODE_DATA_NOTAVAILABLE,
//                                ERROR_MESSAGE_DATA_NOTAVAILABLE)
                        mNavigator?.onLoading(false)
                    })
        }
    }

    fun markAsFavorite(match: Match?, matchType: String?) {
        val homeBandage =
                mRepository?.getTeamBandage(match?.homeId)
        val awayBandage =
                mRepository?.getTeamBandage(match?.awayId)
        val matchPass = Match(
                null,
                match?.uniqueId as String,
                match.round,
                match.date,
                match.time,
                match.homeId,
                match.homeName,
                match.homeScore,
                match.homeFormation,
                match.homeGoalDetails,
                match.homeShoots,
                match.homeLineupGoalkeeper,
                match.homeLineupDefense,
                match.homeLineupMidfield,
                match.homeLineupForward,
                match.homeLineupSubstitutes,
                match.homeRedCards,
                match.homeYellowCards,
                match.awayId,
                match.awayName,
                match.awayScore,
                match.awayFormation,
                match.awayGoalDetails,
                match.awayShots,
                match.awayLineupGoalkeeper,
                match.awayLineupDefense,
                match.awayLineupMidfield,
                match.awayLineupForward,
                match.awayLineupSubstitutes,
                match.awayRedCards,
                match.awayYellowCards,
                homeBandage,
                awayBandage,
                matchType)
        mRepository?.storeFavoriteMatch(matchPass)
        mNavigator?.makeToast("mark as favorite")
    }

    fun removeFromFavorite(eventId: Int?) {
        mRepository?.deleteFavoriteMatch(eventId)
        mNavigator?.makeToast("remove from favorite")
    }

    fun favoriteState(eventId: Int?) {
        val favorite = mRepository?.getMatchFavoriteState(eventId)
        if (favorite !== null) {
            favorite.let {
                mFavoriteEvent = !it.isEmpty()
            }
        }
    }

    fun getDataResult(): LiveData<Match> = mMatchDetail
    fun getFavoriteState(): Boolean = mFavoriteEvent

}