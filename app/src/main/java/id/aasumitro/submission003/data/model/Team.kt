package id.aasumitro.submission003.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_BANDAGE
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_NAME
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_NAME_SHORT
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_STADIUM
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_UID
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_TEAMS)
data class Team(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = TABLE_TEAMS_COLUMN_UID)
        @SerializedName("idTeam") val uid: String?,
        @ColumnInfo(name = TABLE_TEAMS_COLUMN_NAME)
        @SerializedName("strTeam") var name: String?,
        @ColumnInfo(name = TABLE_TEAMS_COLUMN_NAME_SHORT)
        @SerializedName("strTeamShort") var shortName: String?,
        @ColumnInfo(name = TABLE_TEAMS_COLUMN_BANDAGE)
        @SerializedName("strTeamBadge") var bandage: String?,
        @ColumnInfo(name = TABLE_TEAMS_COLUMN_STADIUM)
        @SerializedName("strStadium") var stadium: String?
) : Parcelable