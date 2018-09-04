package id.aasumitro.submission004.data.sources.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import id.aasumitro.submission004.data.models.Favorite
import id.aasumitro.submission004.data.models.Team
import id.aasumitro.submission004.util.AppConst
import id.aasumitro.submission004.util.AppConst.DATABASE_NAME

@Database(
        entities =
        [
            (Team::class),
            (Favorite::class)
        ],
        version = 1
)
abstract class DBEvent : RoomDatabase() {

    abstract fun eventDAO(): EventDAO

    companion object {

        @Volatile private var INSTANCE: DBEvent? = null

        fun getInstance(context: Context): DBEvent =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        DBEvent::class.java, DATABASE_NAME
                ).allowMainThreadQueries().build()

    }

}
