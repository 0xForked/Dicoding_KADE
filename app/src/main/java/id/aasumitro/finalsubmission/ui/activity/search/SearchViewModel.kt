package id.aasumitro.finalsubmission.ui.activity.search

import android.arch.lifecycle.ViewModel
import id.aasumitro.finalsubmission.data.model.Match
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.util.GlobalConst.SEARCH_MATCH
import id.aasumitro.finalsubmission.util.GlobalConst.SEARCH_TEAM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {

    private var mNavigator: SearchNavigator? = null
    private var mRepository: Repository? = null

    var mTeamData = ArrayList<Team>()
    var mMatchData = ArrayList<Match>()

    fun initVM(navigator: SearchNavigator,
               repository: Repository) {
        this.mNavigator = navigator
        this.mRepository = repository
    }

    fun startTask(searchType: String?, query: String?) {
        mNavigator?.onLoading(true)
        mNavigator?.clearRecyclerView()
        when (searchType) {
            SEARCH_TEAM -> {
                searchTeam(query)
            }
            SEARCH_MATCH -> {
                searchMatch(query)
            }
        }
    }

    private fun searchMatch(query: String?) {
        mRepository.let {
            it?.getMatchByName(query)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ onSuccess ->
                        val result = onSuccess.matchList as ArrayList<Match>
                        mMatchData = result
                        mNavigator?.initListAdapter()
                        mNavigator?.onLoading(false)
                    }, { onError ->
                        onError.printStackTrace()
                        mNavigator?.onLoading(false)
                    })
        }
    }

    private fun searchTeam(query: String?) {
        mRepository.let {
            it?.getTeamByName(query)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ onSuccess ->
                        val result = onSuccess.teamList as ArrayList<Team>
                        mTeamData = result
                        mNavigator?.initListAdapter()
                        mNavigator?.onLoading(false)
                    }, { onError ->
                        onError.printStackTrace()
                        mNavigator?.onLoading(false)
                    })
        }
    }

}