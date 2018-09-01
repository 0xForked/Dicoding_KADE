package id.aasumitro.submission003.data.source.local

import android.arch.persistence.room.Room
import android.content.Context
import id.aasumitro.submission003.util.AppConst.DATABASE_NAME

object DBCreator {
    fun database(context: Context): AppDB {
        return Room.databaseBuilder(
                context,
                AppDB::class.java,
                DATABASE_NAME
        ).allowMainThreadQueries().build()
    }
}