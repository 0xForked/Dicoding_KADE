package id.aasumitro.submission002.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import id.aasumitro.submission002.data.model.Team
import io.reactivex.Flowable

@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetTeams(teams: ArrayList<Team>)

    @Query("SELECT bandage FROM epl_teams WHERE uid IN(:uid)")
    fun getTeamBandage(uid: String?): String

    @Query("SELECT stadium FROM epl_teams WHERE uid IN(:uid)")
    fun getTeamStadium(uid: String?): String

    @Query("SELECT count(id) from epl_teams")
    fun getDataCount(): Int

}