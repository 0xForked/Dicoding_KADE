package id.aasumitro.finalsubmission.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import id.aasumitro.finalsubmission.data.model.Match
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.data.source.local.DbConst.DATABASE_NAME

@Database(
        entities =
        [
            (Match::class),
            (Team::class)
        ],
        version = 1
)
abstract class RealFootballDb : RoomDatabase() {
    abstract fun realFootballDao(): RealFootballDao
    companion object {
        @Volatile private var INSTANCE: RealFootballDb? = null
        fun getInstance(context: Context): RealFootballDb =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }
        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        RealFootballDb::class.java, DATABASE_NAME
                ).allowMainThreadQueries().build()
    }
}