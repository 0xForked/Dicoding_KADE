package id.aasumitro.finalsubmission.ui.activity.detail.team

import android.arch.lifecycle.ViewModel
import android.util.Log
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.data.source.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TeamDetailViewModel : ViewModel() {
    private var mNavigator: TeamDetailNavigator? = null
    private var mRepository: Repository? = null

    fun initVM(navigator: TeamDetailNavigator,
               repository: Repository) {
        this.mNavigator = navigator
        this.mRepository = repository
    }

    fun favoriteTask(teamId: String?) {
        val isTeam = mRepository?.getTeamById(teamId?.toInt())
        if (isTeam !== null) {
            markAsFavorite(teamId)
        } else {
            mNavigator?.progressDialog(true)
            teamTask(teamId)
        }
    }

    private fun teamTask(teamId: String?) {
        mRepository.let {
            it?.getTeamDetail(teamId?.toInt())
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ onSuccess ->
                val dataResult = onSuccess.teamList as ArrayList<Team>
                mRepository?.storeTeams(dataResult)
                markAsFavorite(dataResult[0].uniqueId)
                mNavigator?.progressDialog(false)
            }, { e -> e.printStackTrace() })
        }
    }

    private fun markAsFavorite(teamId: String?) {
        mRepository?.setTeamAsFavorite(teamId)
    }

    fun removeFromFavorite(teamId: String?) {
        mRepository?.removeFromFavorite(teamId)
    }

    fun favoriteState(teamId: String): Boolean =
            mRepository?.getTeamFavoriteState(teamId) as Boolean


}