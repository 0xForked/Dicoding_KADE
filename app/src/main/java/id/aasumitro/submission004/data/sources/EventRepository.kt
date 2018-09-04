package id.aasumitro.submission004.data.sources

import android.content.Context
import android.util.Log
import id.aasumitro.submission004.RealFootball
import id.aasumitro.submission004.data.models.Favorite
import id.aasumitro.submission004.data.models.Results
import id.aasumitro.submission004.data.models.Team
import id.aasumitro.submission004.data.sources.prefs.SharedPrefs.defaultPrefs
import id.aasumitro.submission004.data.sources.prefs.SharedPrefs.get
import id.aasumitro.submission004.data.sources.prefs.SharedPrefs.set
import id.aasumitro.submission004.data.sources.remote.ApiClient

import id.aasumitro.submission004.util.AppConst.FIRST_OPEN_STATUS
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class EventRepository(
        val context: Context? = null,
        private val apiClient: ApiClient? = null
) : EventDataSource {

    override fun getLastMatch(leagueId: Int?):
            Observable<Results.MatchResult>? =
            apiClient?.apiServices()?.lastMatch(leagueId)

    override fun getNextMatch(leagueId: Int?):
            Observable<Results.MatchResult>? =
            apiClient?.apiServices()?.nextMatch(leagueId)

    override fun getDetailMatch(eventId: Int?):
            Observable<Results.MatchResult>? =
            apiClient?.apiServices()?.detailMatch(eventId)

    override fun getTeamData(leagueName: String?):
            Observable<Results.TeamResult>? =
            apiClient?.apiServices()?.teamData(leagueName)

    override fun insertTeams(teams: ArrayList<Team>?) {
        Observable
                .fromCallable {
                    RealFootball()
                            .dbInstance()
                            .eventDAO()
                            .insertTeams(teams as ArrayList<Team>)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.d("insertLeagueTeams",
                            "Inserted ${teams?.size} " +
                                    "teams from API in to local db...")
                }
    }

    override fun updateTeams(teams: ArrayList<Team>?) {
        Observable
                .fromCallable {
                    RealFootball()
                            .dbInstance()
                            .eventDAO()
                            .updateTeams(teams as ArrayList<Team>)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.d("updateLeagueTeams",
                            "Updated ${teams?.size} " +
                                    "teams from API in to local db...")
                }
    }

    override fun deleteTeams() {
        Observable
                .fromCallable {
                    RealFootball()
                            .dbInstance()
                            .eventDAO()
                            .deleteTeams()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.d("deleteLeagueTeams", "Delete all teams")
                }
    }

    override fun getTeamBandage(uid: String?): String? =
            RealFootball()
                    .dbInstance()
                    .eventDAO()
                    .getTeamBandage(uid as String)

    override fun getTeamStadium(uid: String?): String? =
            RealFootball()
                    .dbInstance()
                    .eventDAO()
                    .getTeamStadium(uid as String)

    override fun getDataCount(): Int? =
            RealFootball()
                    .dbInstance()
                    .eventDAO()
                    .getDataCount()

    override fun insertFavorite(favorite: Favorite?) {
        Observable
                .fromCallable {
                    RealFootball()
                            .dbInstance()
                            .eventDAO()
                            .insertFavorite(favorite as Favorite)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.d("insertFavoriteEvent",
                            "Mark event ${favorite?.eventId} " +
                                    "as favorite...")
                }
    }

    override fun deleteFavorite(eid: Int?) {
        Observable
                .fromCallable {
                   RealFootball()
                           .dbInstance()
                           .eventDAO()
                           .deleteFavorite(eid as Int)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.d("deleteFavoriteEvent", "Favorite event")
                }
    }

    override fun getFavorite(): Maybe<List<Favorite>>? =
                    RealFootball()
                            .dbInstance()
                            .eventDAO()
                            .getFavorite()
                            .doOnSuccess {
                                Log.d("getFavoriteData",
                                        "get ${it.size} " +
                                                "favorite data...")
                            }

    override fun getFavoriteState(eid: Int?): String? =
            RealFootball()
                    .dbInstance()
                    .eventDAO()
                    .getFavoriteState(eid as Int)

    override fun isFirstLaunch(): String? {
        val mPrefs =
                defaultPrefs(context?.applicationContext as Context)
        return mPrefs[FIRST_OPEN_STATUS]
    }

    override fun setFirstLaunchStatus(value: String) {
        val mPrefs =
                defaultPrefs(context?.applicationContext as Context)
        mPrefs[FIRST_OPEN_STATUS] = value
    }

}