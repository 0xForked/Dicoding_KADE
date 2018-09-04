package id.aasumitro.submission004.data.sources.local

import android.arch.persistence.room.*
import id.aasumitro.submission004.data.models.Favorite
import id.aasumitro.submission004.data.models.Team
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface EventDAO {

    @Transaction
    fun updateTeams(teams: ArrayList<Team>) {
        deleteTeams()
        insertTeams(teams)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeams(teams: ArrayList<Team>)

    @Query("DELETE FROM epl_teams")
    fun deleteTeams()

    @Query("SELECT bandage FROM epl_teams WHERE uid IN(:uid)")
    fun getTeamBandage(uid: String): String

    @Query("SELECT stadium FROM epl_teams WHERE uid IN(:uid)")
    fun getTeamStadium(uid: String): String

    @Query("SELECT count(id) from epl_teams")
    fun getDataCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM epl_events_favorite WHERE e_id IN(:eid)")
    fun deleteFavorite(eid: Int)

    @Query("SELECT * FROM epl_events_favorite")
    fun getFavorite(): Maybe<List<Favorite>>

    @Query("SELECT e_date FROM epl_events_favorite WHERE e_id IN(:eid)")
    fun getFavoriteState(eid: Int): String

}