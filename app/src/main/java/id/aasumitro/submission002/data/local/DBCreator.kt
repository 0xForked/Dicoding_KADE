package id.aasumitro.submission002.data.local

import android.arch.persistence.room.Room
import android.content.Context
import id.aasumitro.submission002.util.AppConst.DATABASE_NAME

object DBCreator {
    fun database(context: Context): AppDB {
        return Room.databaseBuilder(
                context,
                AppDB::class.java,
                DATABASE_NAME
        ).allowMainThreadQueries().build()
    }
}