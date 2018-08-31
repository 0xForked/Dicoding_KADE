package id.aasumitro.submission002.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match (
        @SerializedName("idEvent")
        var eventId: String,
        @SerializedName("intRound")
        var eventRound: String? = null,
        @SerializedName("dateEvent")
        var eventDate: String,
        @SerializedName("strTime")
        var eventTime: String,
        @SerializedName("idHomeTeam")
        var homeId: String,
        @SerializedName("idAwayTeam")
        var awayId: String,
        @SerializedName("strHomeTeam")
        var homeName: String,
        @SerializedName("strAwayTeam")
        var awayName: String,
        @SerializedName("intHomeScore")
        var homeScore: String? = null,
        @SerializedName("intAwayScore")
        var awayScore: String? = null,
        @SerializedName("strHomeFormation")
        var homeFormation: String? = null,
        @SerializedName("strAwayFormation")
        var awayFormation: String? = null,
        @SerializedName("strHomeGoalDetails")
        var homeGoalDetails: String? = null,
        @SerializedName("strAwayGoalDetails")
        var awayGoalDetails: String? = null,
        @SerializedName("intHomeShots")
        var homeShoots: String? = null,
        @SerializedName("intAwayShots")
        var awayShots: String? = null,
        @SerializedName("strHomeLineupGoalkeeper")
        var homeLineupGoalkeeper: String? = null,
        @SerializedName("strAwayLineupGoalkeeper")
        var awayLineupGoalkeeper: String? = null,
        @SerializedName("strHomeLineupDefense")
        var homeLineupDefense: String? = null,
        @SerializedName("strAwayLineupDefense")
        var awayLineupDefense: String? = null,
        @SerializedName("strHomeLineupMidfield")
        var homeLineupMidfield: String? = null,
        @SerializedName("strAwayLineupMidfield")
        var awayLineupMidfield: String? = null,
        @SerializedName("strHomeLineupForward")
        var homeLineupForward: String? = null,
        @SerializedName("strAwayLineupForward")
        var awayLineupForward: String? = null,
        @SerializedName("strHomeLineupSubstitutes")
        var homeLineupSubstitutes: String? = null,
        @SerializedName("strAwayLineupSubstitutes")
        var awayLineupSubstitutes: String? = null
) : Parcelable
