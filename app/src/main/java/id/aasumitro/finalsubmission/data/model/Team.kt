package id.aasumitro.finalsubmission.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_ALT_NAME
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_BANDAGE
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_COUNTRY
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_DESCRIPTION
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_FACEBOOK
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_FORMED_YEAR
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_INSTAGRAM
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_IS_FAVORITE
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_KEYWORD
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_LEAGUE_ID
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_LEAGUE_NAME
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_MANAGER
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_NAME
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_SHORT_NAME
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_STADIUM
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_TWITTER
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_UNIQUE_ID
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_WEBSITE
import id.aasumitro.finalsubmission.data.source.local.DbConst.TABLE_TEAM_YOUTUBE
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_TEAM, indices = [
        Index(value = [TABLE_TEAM_UNIQUE_ID], unique = true)
])
data class Team(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = TABLE_TEAM_UNIQUE_ID)
        @SerializedName("idTeam")
        val uniqueId: String,
        @ColumnInfo(name = TABLE_TEAM_NAME)
        @SerializedName("strTeam")
        var name: String,
        @ColumnInfo(name = TABLE_TEAM_SHORT_NAME)
        @SerializedName("strTeamShort")
        var shortName: String? = null,
        @ColumnInfo(name = TABLE_TEAM_ALT_NAME)
        @SerializedName("strAlternate")
        var altName: String? = null,
        @ColumnInfo(name = TABLE_TEAM_FORMED_YEAR)
        @SerializedName("intFormedYear")
        var formedYear: String? = null,
        @ColumnInfo(name = TABLE_TEAM_LEAGUE_NAME)
        @SerializedName("strLeague")
        var leagueName: String? = null,
        @ColumnInfo(name = TABLE_TEAM_LEAGUE_ID)
        @SerializedName("idLeague")
        var leagueId: String? = null,
        @ColumnInfo(name = TABLE_TEAM_MANAGER)
        @SerializedName("strManager")
        var manager: String? = null,
        @ColumnInfo(name = TABLE_TEAM_STADIUM)
        @SerializedName("strStadium")
        var stadium: String? = null,
        @ColumnInfo(name = TABLE_TEAM_KEYWORD)
        @SerializedName("strKeywords")
        var keyword: String? = null,
        @ColumnInfo(name = TABLE_TEAM_WEBSITE)
        @SerializedName("strWebsite")
        var website: String? = null,
        @ColumnInfo(name = TABLE_TEAM_FACEBOOK)
        @SerializedName("strFacebook")
        var facebook: String? = null,
        @ColumnInfo(name = TABLE_TEAM_TWITTER)
        @SerializedName("strTwitter")
        var twitter: String? = null,
        @ColumnInfo(name = TABLE_TEAM_INSTAGRAM)
        @SerializedName("strInstagram")
        var instagram: String? = null,
        @ColumnInfo(name = TABLE_TEAM_YOUTUBE)
        @SerializedName("strYoutube")
        var youtube: String? = null,
        @ColumnInfo(name = TABLE_TEAM_DESCRIPTION)
        @SerializedName("strDescriptionEN")
        var description: String? = null,
        @ColumnInfo(name = TABLE_TEAM_COUNTRY)
        @SerializedName("strCountry")
        var country: String? = null,
        @ColumnInfo(name = TABLE_TEAM_BANDAGE)
        @SerializedName("strTeamBadge")
        var bandage: String? = null,
        @ColumnInfo(name = TABLE_TEAM_IS_FAVORITE)
        var isFavorite: Boolean = false
) : Parcelable