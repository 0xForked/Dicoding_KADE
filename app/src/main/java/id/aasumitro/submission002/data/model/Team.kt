package id.aasumitro.submission002.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import id.aasumitro.submission002.util.AppConst.DATABASE_TABLE
import id.aasumitro.submission002.util.AppConst.DATABASE_TABLE_COLUMN_BANDAGE
import id.aasumitro.submission002.util.AppConst.DATABASE_TABLE_COLUMN_NAME
import id.aasumitro.submission002.util.AppConst.DATABASE_TABLE_COLUMN_NAME_SHORT
import id.aasumitro.submission002.util.AppConst.DATABASE_TABLE_COLUMN_STADIUM
import id.aasumitro.submission002.util.AppConst.DATABASE_TABLE_COLUMN_UID

@Entity(tableName = DATABASE_TABLE)
data class Team(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_UID)
        @SerializedName("idTeam") val uid: String?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_NAME)
        @SerializedName("strTeam") var name: String?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_NAME_SHORT)
        @SerializedName("strTeamShort") var shortName: String?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_BANDAGE)
        @SerializedName("strTeamBadge") var bandage: String?,
        @ColumnInfo(name = DATABASE_TABLE_COLUMN_STADIUM)
        @SerializedName("strStadium") var stadium: String?
)