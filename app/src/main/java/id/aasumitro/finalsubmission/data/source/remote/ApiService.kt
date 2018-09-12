package id.aasumitro.finalsubmission.data.source.remote

import id.aasumitro.finalsubmission.data.model.DataResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // API Request format
    // l (league)
    //      ENGLISH20%PREMIER20%LEAGUE or
    //      ENGLISH_PREMIER_LEAGUE
    // d (date)
    //      2018-01-01

    @GET("search_all_leagues.php?s=Soccer")
    fun league(): Observable<DataResult.LeagueResult>

    @GET("eventspastleague.php")
    fun lastMatch(@Query("id") id: Int?):
            Observable<DataResult.EventsResult>

    @GET("eventsnextleague.php")
    fun nextMatch(@Query("id") id: Int?):
            Observable<DataResult.EventsResult>

    @GET("lookupevent.php")
    fun detailMatch(@Query("id") id: Int?):
            Observable<DataResult.EventsResult>

    @GET("searchevents.php")
    fun searchMatch(@Query("e") eventName: String?):
            Observable<DataResult.EventResult>

    @GET("search_all_teams.php")
    fun teams(@Query("l") leagueName: String?):
            Observable<DataResult.TeamResult>

    @GET("lookupteam.php")
    fun detailTeams(@Query("id") teamId: Int?):
            Observable<DataResult.TeamResult>

    @GET("searchteams.php")
    fun searchTeam(@Query("t") teamName: String?):
            Observable<DataResult.TeamResult>

    @GET("lookup_all_players.php")
    fun players(@Query("id") teamId: Int?):
            Observable<DataResult.PlayerResult>

}