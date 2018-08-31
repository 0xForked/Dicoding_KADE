package id.aasumitro.submission002.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import id.aasumitro.submission002.data.model.Team

@Database(entities = [(Team::class)], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun teamDAO(): TeamDao
}