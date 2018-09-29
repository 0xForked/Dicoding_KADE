package id.aasumitro.finalsubmission.data.source

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import id.aasumitro.finalsubmission.RealFootball
import id.aasumitro.finalsubmission.data.model.DataResult
import id.aasumitro.finalsubmission.data.model.Match
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.defaultPrefs
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.get
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.set
import id.aasumitro.finalsubmission.data.source.remote.ApiClient
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.InputStreamReader

class Repository(private val context: Context? = null,
                 private val apiClient: ApiClient = ApiClient())  {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun getLeague(): JsonObject? {
        try {
            context?.assets?.open("league.json").use { `is` ->
                val parser = JsonParser()
                return parser.parse(InputStreamReader(`is`)).asJsonObject
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getRemoteLeague():
            Observable<DataResult.LeagueResult>? =
            apiClient.apiServices()?.league()

    fun getLastMatch(leagueId: Int?):
             Observable<DataResult.EventsResult>? =
             apiClient.apiServices()?.lastMatch(leagueId)

    fun getNextMatch(leagueId: Int?):
            Observable<DataResult.EventsResult>? =
            apiClient.apiServices()?.nextMatch(leagueId)

    fun getMatchDetail(eventId: Int?):
             Observable<DataResult.EventsResult>? =
             apiClient.apiServices()?.detailMatch(eventId)

    fun getMatchByName(eventName: String?):
             Observable<DataResult.EventResult>? =
             apiClient.apiServices()?.searchMatch(eventName)

    fun getRemoteTeams(league: String?):
             Observable<DataResult.TeamResult>? =
             apiClient.apiServices()?.teams(league)

    fun getTeamDetail(teamId: Int?):
             Observable<DataResult.TeamResult>? =
             apiClient.apiServices()?.detailTeams(teamId)

    fun getTeamByName(teamName: String?):
             Observable<DataResult.TeamResult>? =
             apiClient.apiServices()?.searchTeam(teamName)

    fun getTeamById(teamId: Int?) =
            RealFootball()
                    .instanceDb()
                    .realFootballDao()
                    .readTeamById(teamId as Int)

    fun getPlayers(teamId: Int?):
             Observable<DataResult.PlayerResult>? =
             apiClient.apiServices()?.players(teamId)

    fun storeFavoriteMatch(match: Match?) {
        Observable
                .fromCallable {
                    RealFootball()
                            .instanceDb()
                            .realFootballDao()
                            .insertFavoriteMatch(match as Match)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.d("insertFavoriteEvent",
                            "Mark event ${match?.uniqueId} " +
                                    "as favorite...")
                }
    }

    fun deleteFavoriteMatch(matchId: Int?) {
        Observable
                .fromCallable {
                    RealFootball()
                            .instanceDb()
                            .realFootballDao()
                            .deleteFavoriteMatch(matchId as Int)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.d("deleteFavoriteEvent", "Favorite event")
                }
    }

    fun getFavoriteMatch(): Maybe<List<Match>>? =
            RealFootball()
                    .instanceDb()
                    .realFootballDao()
                    .readFavoriteMatch()
                    .doOnSuccess {
                        Log.d("getFavoriteData",
                                "get ${it.size} " +
                                        "favorite data...")
                    }

    fun getMatchFavoriteState(matchId: Int?) =
            RealFootball()
                    .instanceDb()
                    .realFootballDao()
                    .readMatchFavoriteState(matchId as Int)

    fun storeTeams(teams: ArrayList<Team>?) {
        Observable
                .fromCallable {
                    RealFootball()
                            .instanceDb()
                            .realFootballDao()
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

    fun updateTeams(teams: ArrayList<Team>?) {
        Observable
                .fromCallable {
                    RealFootball()
                            .instanceDb()
                            .realFootballDao()
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

    fun getLocalTeams(leagueName: String): Maybe<List<Team>>? =
            RealFootball()
                    .instanceDb()
                    .realFootballDao()
                    .readTeams(leagueName)
                    .doOnSuccess {
                        Log.d("getLocalTeams",
                                "get ${it.size} " +
                                        "favorite data...")
                    }

    fun getFavoriteTeams(): Maybe<List<Team>>? =
            RealFootball()
                    .instanceDb()
                    .realFootballDao()
                    .readFavoriteTeams()
                    .doOnSuccess {
                        Log.d("getFavoriteData",
                                "get ${it.size} " +
                                        "favorite data...")
                    }

    fun getTeamBandage(uid: String?): String? =
            RealFootball()
                    .instanceDb()
                    .realFootballDao()
                    .readTeamBandage(uid as String)

    fun getTeamStadium(uid: String?): String? =
            RealFootball()
                    .instanceDb()
                    .realFootballDao()
                    .readTeamStadium(uid as String)

    fun getTeamCountByLeague(leagueName: String?): Int? =
            RealFootball()
                    .instanceDb()
                    .realFootballDao()
                    .readTeamCountByLeague(leagueName as String)

    fun getTeamFavoriteState(uid: String?): Boolean? =
            RealFootball()
                    .instanceDb()
                    .realFootballDao()
                    .readTeamFavoriteState(uid as String)

    fun setTeamAsFavorite(uid: String?) {
        Observable
                .fromCallable {
                    RealFootball()
                            .instanceDb()
                            .realFootballDao()
                            .setTeamAsFavorite(uid as String)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.d("markAsFavorite", "Mark $uid as Favorite")
                }
    }

    fun removeFromFavorite(uid: String?) {
        Observable
                .fromCallable {
                    RealFootball()
                            .instanceDb()
                            .realFootballDao()
                            .removeTeamFromFavorite(uid as String)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe {
                    Log.d("removeFavorite", "Remove $uid from Favorite")
                }
    }

    fun getPrefs(key: String): String? {
        val mPrefs =
                defaultPrefs(context?.applicationContext as Context)
        return mPrefs[key]
    }

    fun setPrefs(key: String, value: String) {
        val mPrefs =
                defaultPrefs(context?.applicationContext as Context)
        mPrefs[key] = value
    }

}