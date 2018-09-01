package id.aasumitro.submission003.data.source.local.anko

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import id.aasumitro.submission003.util.AppConst.DATABASE_NAME
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_BANDAGE_AWAY_TEAM
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_BANDAGE_HOME_TEAM
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_EVENT_DATE
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_EVENT_ID
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_EVENT_TIME
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_ID_AWAY_TEAM
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_ID_HOME_TEAM
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_NAME_AWAY_TEAM
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_NAME_HOME_TEAM
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_SCORE_AWAY_TEAM
import id.aasumitro.submission003.util.AppConst.TABLE_FAVORITE_COLUMN_SCORE_HOME_TEAM
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_BANDAGE
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_NAME
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_NAME_SHORT
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_STADIUM
import id.aasumitro.submission003.util.AppConst.TABLE_TEAMS_COLUMN_UID
import org.jetbrains.anko.db.*

class AnkoHelper(context: Context) :
        ManagedSQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        private var instance: AnkoHelper? = null
        @Synchronized
        fun getInstance(ctx: Context): AnkoHelper {
            if (instance == null) {
                instance = AnkoHelper(ctx.applicationContext)
            }
            return instance as AnkoHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(TABLE_FAVORITE, true,
                "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                TABLE_FAVORITE_COLUMN_EVENT_ID to TEXT + UNIQUE,
                TABLE_FAVORITE_COLUMN_EVENT_DATE to TEXT,
                TABLE_FAVORITE_COLUMN_EVENT_TIME to TEXT,
                TABLE_FAVORITE_COLUMN_ID_HOME_TEAM to TEXT,
                TABLE_FAVORITE_COLUMN_NAME_HOME_TEAM to TEXT,
                TABLE_FAVORITE_COLUMN_SCORE_HOME_TEAM to TEXT,
                TABLE_FAVORITE_COLUMN_BANDAGE_HOME_TEAM to TEXT,
                TABLE_FAVORITE_COLUMN_ID_AWAY_TEAM to TEXT,
                TABLE_FAVORITE_COLUMN_NAME_AWAY_TEAM to TEXT,
                TABLE_FAVORITE_COLUMN_SCORE_AWAY_TEAM to TEXT,
                TABLE_FAVORITE_COLUMN_BANDAGE_AWAY_TEAM to TEXT)
        db?.createTable(TABLE_TEAMS, false,
                "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                TABLE_TEAMS_COLUMN_UID to TEXT + UNIQUE,
                TABLE_TEAMS_COLUMN_NAME to TEXT,
                TABLE_TEAMS_COLUMN_NAME_SHORT to TEXT,
                TABLE_TEAMS_COLUMN_BANDAGE to TEXT,
                TABLE_TEAMS_COLUMN_STADIUM to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(TABLE_FAVORITE, true)
        db?.dropTable(TABLE_TEAMS, true)
    }

}
