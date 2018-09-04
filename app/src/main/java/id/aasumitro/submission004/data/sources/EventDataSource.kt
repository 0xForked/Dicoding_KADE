package id.aasumitro.submission004.data.sources

import id.aasumitro.submission004.data.models.Favorite
import id.aasumitro.submission004.data.models.Results
import id.aasumitro.submission004.data.models.Team
import io.reactivex.Maybe
import io.reactivex.Observable

interface EventDataSource {

    fun getLastMatch(leagueId: Int?): Observable<Results.MatchResult>?

    fun getNextMatch(leagueId: Int?): Observable<Results.MatchResult>?

    fun getDetailMatch(eventId: Int?): Observable<Results.MatchResult>?

    fun getTeamData(leagueName: String?): Observable<Results.TeamResult>?

    fun insertTeams(teams: ArrayList<Team>?)

    fun updateTeams(teams: ArrayList<Team>?)

    fun deleteTeams()

    fun getTeamBandage(uid: String?): String?

    fun getTeamStadium(uid: String?): String?

    fun getDataCount(): Int?

    fun insertFavorite(favorite: Favorite?)

    fun deleteFavorite(eid: Int?)

    fun getFavorite(): Maybe<List<Favorite>>?

    fun getFavoriteState(eid: Int?): String?

    fun isFirstLaunch(): String?

    fun setFirstLaunchStatus(value: String)

}