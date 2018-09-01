package id.aasumitro.submission003.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_BANDAGE_AWAY_TEAM
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_BANDAGE_HOME_TEAM
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_EVENT_DATE
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_EVENT_ID
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_EVENT_TIME
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_ID_AWAY_TEAM
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_ID_HOME_TEAM
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_NAME_AWAY_TEAM
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_NAME_HOME_TEAM
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_SCORE_AWAY_TEAM
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_SCORE_HOME_TEAM
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_FAVORITE)
data class Favorite(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = TABLE_FAVORITE_COLUMN_EVENT_ID)
        @SerializedName("idEvent") var eventId: String,
        @ColumnInfo(name = TABLE_FAVORITE_COLUMN_EVENT_DATE)
        @SerializedName("dateEvent") var eventDate: String,
        @ColumnInfo(name = TABLE_FAVORITE_COLUMN_EVENT_TIME)
        @SerializedName("strTime") var eventTime: String,
        @ColumnInfo(name = TABLE_FAVORITE_COLUMN_ID_HOME_TEAM)
        @SerializedName("idHomeTeam") var homeId: String,
        @ColumnInfo(name = TABLE_FAVORITE_COLUMN_NAME_HOME_TEAM)
        @SerializedName("strHomeTeam") var homeName: String,
        @ColumnInfo(name = TABLE_FAVORITE_COLUMN_SCORE_HOME_TEAM)
        @SerializedName("intHomeScore") var homeScore: String? = null,
        @ColumnInfo(name = TABLE_FAVORITE_COLUMN_BANDAGE_HOME_TEAM)
        var homeBandage: String? = null,
        @ColumnInfo(name = TABLE_FAVORITE_COLUMN_ID_AWAY_TEAM)
        @SerializedName("idAwayTeam") var awayId: String,
        @ColumnInfo(name = TABLE_FAVORITE_COLUMN_NAME_AWAY_TEAM)
        @SerializedName("strAwayTeam") var awayName: String,
        @ColumnInfo(name = TABLE_FAVORITE_COLUMN_SCORE_AWAY_TEAM)
        @SerializedName("intAwayScore") var awayScore: String? = null,
        @ColumnInfo(name = TABLE_FAVORITE_COLUMN_BANDAGE_AWAY_TEAM)
        var awayBandage: String? = null
) : Parcelable