package id.aasumitro.submission003.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import id.aasumitro.submission003.data.model.Favorite
import id.aasumitro.submission003.data.model.Team

@Database(
        entities =
        [
            (Team::class),
            (Favorite::class)
        ],
        version = 1
)
abstract class AppDB : RoomDatabase() {
    abstract fun teamDAO(): TeamDao
}