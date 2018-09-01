package id.aasumitro.submission003.data.source.local

import android.arch.persistence.room.*
import id.aasumitro.submission003.data.model.Team

@Dao
interface TeamDao {

    // after validate teams local and remote
    // if local data != remote data
    // then do this action
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
    fun getTeamBandage(uid: String?): String

    @Query("SELECT stadium FROM epl_teams WHERE uid IN(:uid)")
    fun getTeamStadium(uid: String?): String

    @Query("SELECT count(id) from epl_teams")
    fun getDataCount(): Int

}