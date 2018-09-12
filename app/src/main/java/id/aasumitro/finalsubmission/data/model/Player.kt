package id.aasumitro.finalsubmission.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
        @SerializedName("idTeam")
        val teamId: String,
        @SerializedName("idPlayerManager")
        val managerId: String? = null,
        @SerializedName("idPlayer")
        val id: String,
        @SerializedName("strNationality")
        val nationality: String,
        @SerializedName("strPlayer")
        var name: String,
        @SerializedName("dateBorn")
        var born: String? = null,
        @SerializedName("strBirthLocation")
        var birthLocation: String? = null,
        @SerializedName("dateSigned")
        var signed: String? = null,
        @SerializedName("strSigning")
        var signing: String? = null,
        @SerializedName("strWage")
        var wage: String? = null,
        @SerializedName("strGender")
        var gender: String? = null,
        @SerializedName("strPosition")
        var position: String? = null,
        @SerializedName("strCollege")
        var college: String? = null,
        @SerializedName("strWebsite")
        var website: String? = null,
        @SerializedName("strFacebook")
        var facebook: String? = null,
        @SerializedName("strTwitter")
        var twitter: String? = null,
        @SerializedName("strInstagram")
        var instagram: String? = null,
        @SerializedName("strYoutube")
        var youtube: String? = null,
        @SerializedName("strDescriptionEN")
        var description: String? = null,
        @SerializedName("strHeight")
        var height: String? = null,
        @SerializedName("strWeight")
        var weight: String? = null,
        @SerializedName("strThumb")
        var image: String? = null
) : Parcelable