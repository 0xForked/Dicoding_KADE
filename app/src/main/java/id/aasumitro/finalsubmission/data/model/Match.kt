package id.aasumitro.finalsubmission.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_AWAY_BANDAGE
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_AWAY_FORMATION
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_AWAY_GOAL_DETAILS
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_AWAY_ID
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_AWAY_LINEUP_DEFENSE
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_AWAY_LINEUP_FORWARD
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_AWAY_LINEUP_GOALKEEPER
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_AWAY_LINEUP_MIDFIELD
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_AWAY_LINEUP_SUBSTITUTES
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_AWAY_NAME
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_AWAY_RED_CARDS
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_AWAY_SCORE
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_AWAY_SHOOTS
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_AWAY_YELLOW_CARDS
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_DATE
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_FAVORITE_TYPE
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_HOME_BANDAGE
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_HOME_FORMATION
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_HOME_GOAL_DETAILS
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_HOME_ID
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_HOME_LINEUP_DEFENSE
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_HOME_LINEUP_FORWARD
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_HOME_LINEUP_GOALKEEPER
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_HOME_LINEUP_MIDFIELD
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_HOME_LINEUP_SUBSTITUTES
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_HOME_NAME
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_HOME_RED_CARDS
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_HOME_SCORE
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_HOME_SHOOTS
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_HOME_YELLOW_CARDS
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_ROUND
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_TIME
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_MATCH_UNIQUE_ID
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_MATCH)
data class Match (
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = TABLE_MATCH_UNIQUE_ID)
        @SerializedName("idEvent")
        var uniqueId: String,
        @ColumnInfo(name = TABLE_MATCH_ROUND)
        @SerializedName("intRound")
        var round: String? = null,
        @ColumnInfo(name = TABLE_MATCH_DATE)
        @SerializedName("dateEvent")
        var date: String,
        @ColumnInfo(name = TABLE_MATCH_TIME)
        @SerializedName("strTime")
        var time: String,
        @ColumnInfo(name = TABLE_MATCH_HOME_ID)
        @SerializedName("idHomeTeam")
        var homeId: String,
        @ColumnInfo(name = TABLE_MATCH_HOME_NAME)
        @SerializedName("strHomeTeam")
        var homeName: String,
        @ColumnInfo(name = TABLE_MATCH_HOME_SCORE)
        @SerializedName("intHomeScore")
        var homeScore: String? = null,
        @ColumnInfo(name = TABLE_MATCH_HOME_FORMATION)
        @SerializedName("strHomeFormation")
        var homeFormation: String? = null,
        @ColumnInfo(name = TABLE_MATCH_HOME_GOAL_DETAILS)
        @SerializedName("strHomeGoalDetails")
        var homeGoalDetails: String? = null,
        @ColumnInfo(name = TABLE_MATCH_HOME_SHOOTS)
        @SerializedName("intHomeShots")
        var homeShoots: String? = null,
        @ColumnInfo(name = TABLE_MATCH_HOME_LINEUP_GOALKEEPER)
        @SerializedName("strHomeLineupGoalkeeper")
        var homeLineupGoalkeeper: String? = null,
        @ColumnInfo(name = TABLE_MATCH_HOME_LINEUP_DEFENSE)
        @SerializedName("strHomeLineupDefense")
        var homeLineupDefense: String? = null,
        @ColumnInfo(name = TABLE_MATCH_HOME_LINEUP_MIDFIELD)
        @SerializedName("strHomeLineupMidfield")
        var homeLineupMidfield: String? = null,
        @ColumnInfo(name = TABLE_MATCH_HOME_LINEUP_FORWARD)
        @SerializedName("strHomeLineupForward")
        var homeLineupForward: String? = null,
        @ColumnInfo(name = TABLE_MATCH_HOME_LINEUP_SUBSTITUTES)
        @SerializedName("strHomeLineupSubstitutes")
        var homeLineupSubstitutes: String? = null,
        @ColumnInfo(name = TABLE_MATCH_HOME_RED_CARDS)
        @SerializedName("strHomeRedCards")
        var homeRedCards: String? = null,
        @ColumnInfo(name = TABLE_MATCH_HOME_YELLOW_CARDS)
        @SerializedName("strHomeYellowCards")
        var homeYellowCards: String? = null,
        @ColumnInfo(name = TABLE_MATCH_AWAY_ID)
        @SerializedName("idAwayTeam")
        var awayId: String,
        @ColumnInfo(name = TABLE_MATCH_AWAY_NAME)
        @SerializedName("strAwayTeam")
        var awayName: String,
        @ColumnInfo(name = TABLE_MATCH_AWAY_SCORE)
        @SerializedName("intAwayScore")
        var awayScore: String? = null,
        @ColumnInfo(name = TABLE_MATCH_AWAY_FORMATION)
        @SerializedName("strAwayFormation")
        var awayFormation: String? = null,
        @ColumnInfo(name = TABLE_MATCH_AWAY_GOAL_DETAILS)
        @SerializedName("strAwayGoalDetails")
        var awayGoalDetails: String? = null,
        @ColumnInfo(name = TABLE_MATCH_AWAY_SHOOTS)
        @SerializedName("intAwayShots")
        var awayShots: String? = null,
        @ColumnInfo(name = TABLE_MATCH_AWAY_LINEUP_GOALKEEPER)
        @SerializedName("strAwayLineupGoalkeeper")
        var awayLineupGoalkeeper: String? = null,
        @ColumnInfo(name = TABLE_MATCH_AWAY_LINEUP_DEFENSE)
        @SerializedName("strAwayLineupDefense")
        var awayLineupDefense: String? = null,
        @ColumnInfo(name = TABLE_MATCH_AWAY_LINEUP_MIDFIELD)
        @SerializedName("strAwayLineupMidfield")
        var awayLineupMidfield: String? = null,
        @ColumnInfo(name = TABLE_MATCH_AWAY_LINEUP_FORWARD)
        @SerializedName("strAwayLineupForward")
        var awayLineupForward: String? = null,
        @ColumnInfo(name = TABLE_MATCH_AWAY_LINEUP_SUBSTITUTES)
        @SerializedName("strAwayLineupSubstitutes")
        var awayLineupSubstitutes: String? = null,
        @ColumnInfo(name = TABLE_MATCH_AWAY_RED_CARDS)
        @SerializedName("strAwayRedCards")
        var awayRedCards: String? = null,
        @ColumnInfo(name = TABLE_MATCH_AWAY_YELLOW_CARDS)
        @SerializedName("strAwayYellowCards")
        var awayYellowCards: String? = null,
        @ColumnInfo(name = TABLE_MATCH_HOME_BANDAGE)
        var homeBandage: String? = null,
        @ColumnInfo(name = TABLE_MATCH_AWAY_BANDAGE)
        var awayBandage: String? = null,
        @ColumnInfo(name = TABLE_MATCH_FAVORITE_TYPE)
        var favoriteType: String? = null
) : Parcelable