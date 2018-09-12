package id.aasumitro.finalsubmission.data.source.local

import android.arch.persistence.room.*
import id.aasumitro.finalsubmission.data.model.Match
import id.aasumitro.finalsubmission.data.model.Team
import io.reactivex.Maybe

@Dao
interface RealFootballDao {

    @Transaction
    fun updateTeams(teams: ArrayList<Team>) {
        //deleteTeams()
        insertTeams(teams)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTeams(teams: ArrayList<Team>)

    @Query("DELETE FROM rf_team where rft_is_favorite IN(:isFavorite)")
    fun deleteTeams(isFavorite: Boolean = false)

    @Query("SELECT * FROM rf_team where rft_league_name IN(:leagueName)")
    fun readTeams(leagueName: String): Maybe<List<Team>>

    @Query("SELECT rft_unique_id FROM rf_team WHERE rft_unique_id IN(:eid)")
    fun readTeamById(eid: Int): String

    @Query("SELECT rft_bandage FROM rf_team WHERE rft_unique_id IN(:uid)")
    fun readTeamBandage(uid: String): String

    @Query("SELECT rft_stadium FROM rf_team WHERE rft_unique_id IN(:uid)")
    fun readTeamStadium(uid: String): String

    @Query("SELECT count(rft_unique_id) from rf_team where rft_league_name IN(:leagueName)")
    fun readTeamCountByLeague(leagueName: String): Int

    @Query("UPDATE rf_team set rft_is_favorite = :favorite where rft_unique_id = :uid")
    fun setTeamAsFavorite(uid: String, favorite: Boolean = true)

    @Query("UPDATE rf_team set rft_is_favorite = :favorite where rft_unique_id = :uid")
    fun removeTeamFromFavorite(uid: String, favorite: Boolean = false)

    @Query("SELECT rft_is_favorite FROM rf_team WHERE rft_unique_id IN(:uid)")
    fun readTeamFavoriteState(uid: String): Boolean

    @Query("SELECT * FROM rf_team where rft_is_favorite IN(:isFavorite)")
    fun readFavoriteTeams(isFavorite: Boolean = true): Maybe<List<Team>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMatch(match: Match)

    @Query("DELETE FROM rf_match WHERE rfm_unique_id IN(:eid)")
    fun deleteFavoriteMatch(eid: Int)

    @Query("SELECT * FROM rf_match")
    fun readFavoriteMatch(): Maybe<List<Match>>

    @Query("SELECT rfm_unique_id FROM rf_match WHERE rfm_unique_id IN(:eid)")
    fun readMatchFavoriteState(eid: Int): String

}