package id.aasumitro.submission004.data.sources.remote

import id.aasumitro.submission004.data.models.Results
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

    @GET("eventspastleague.php")
    fun lastMatch(@Query("id") id: Int?): Observable<Results.MatchResult>

    @GET("eventsnextleague.php")
    fun nextMatch(@Query("id") id: Int?): Observable<Results.MatchResult>

    @GET("lookupevent.php")
    fun detailMatch(@Query("id") id: Int?): Observable<Results.MatchResult>

    @GET("search_all_teams.php")
    fun teamData(@Query("l") leagueName: String?): Observable<Results.TeamResult>

}