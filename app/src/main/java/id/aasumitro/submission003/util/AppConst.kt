package id.aasumitro.submission003.util

object AppConst {
    // Preferences data
    const val FIRST_OPEN_STATUS = "IS_FIRST_OPEN"
    const val STATUS_EVER = "EVER"
    const val STATUS_NEVER = "NEVER"

    // Remote data
    const val API_URL = "https://www.thesportsdb.com/api/v1/json/1/"

    // Local data (ROOM)
    const val DATABASE_NAME = "sportsDB"

    const val TABLE_TEAMS = "epl_teams"
    const val TABLE_TEAMS_COLUMN_UID = "uid"
    const val TABLE_TEAMS_COLUMN_NAME = "name"
    const val TABLE_TEAMS_COLUMN_NAME_SHORT = "short_name"
    const val TABLE_TEAMS_COLUMN_BANDAGE = "bandage"
    const val TABLE_TEAMS_COLUMN_STADIUM = "stadium"

    const val TABLE_FAVORITE = "epl_events_favorite"
    const val TABLE_FAVORITE_COLUMN_EVENT_ID = "e_id"
    const val TABLE_FAVORITE_COLUMN_EVENT_DATE = "e_date"
    const val TABLE_FAVORITE_COLUMN_EVENT_TIME = "e_time"
    const val TABLE_FAVORITE_COLUMN_ID_HOME_TEAM = "h_id"
    const val TABLE_FAVORITE_COLUMN_NAME_HOME_TEAM = "h_name"
    const val TABLE_FAVORITE_COLUMN_SCORE_HOME_TEAM = "h_score"
    const val TABLE_FAVORITE_COLUMN_BANDAGE_HOME_TEAM = "h_bandage"
    const val TABLE_FAVORITE_COLUMN_ID_AWAY_TEAM = "a_id"
    const val TABLE_FAVORITE_COLUMN_NAME_AWAY_TEAM = "a_name"
    const val TABLE_FAVORITE_COLUMN_SCORE_AWAY_TEAM = "a_score"
    const val TABLE_FAVORITE_COLUMN_BANDAGE_AWAY_TEAM = "a_bandage"

    // Status
    const val MATCH_STATUS_FT = "FT"
    const val MATCH_STATUS_VS = "VS"
    const val MATCH_VALUE = "MATCH_VALUE"
    const val PREV_MATCH = "PREV_MATCH"
    const val NEXT_MATCH = "NEXT_MATCH"
    const val FAVORITE_MATCH = "FAVORITE_MATCH"
    var GLOBAL_MATCH_VALUE: String? = null

    // Intent
    const val INTENT_DATA_EVENT_ID = "EVENT_ID"

    // Event League
    const val LEAGUE_ID = 4328
    const val LEAGUE = "English Premier League"

    // Bitmap Transform
    const val MAX_WIDTH = 512
    const val MAX_HEIGHT = 512
    var SIZE = Math.ceil(Math.sqrt((MAX_WIDTH * MAX_HEIGHT)
            .toDouble()))
            .toInt()

    // DateTime Formatter/pattern
    const val SERVER_DATE_PATTERN = "yyyy-MM-dd"
    const val SERVER_TIME_PATTERN = "HH:mm:ss+SS:SS"
    const val APP_DATE_PATTERN = "EEE, MMM yyy"
    const val APP_TIME_PATTERN = "hh:mm"

    // Error Code and Message
    const val ERROR_CODE_NETWORK_NOTAVAILABLE = "NETWORK_NOT_AVAILABLE"
    const val ERROR_CODE_UNKNOWN_ERROR = "UNKNOWN_ERROR"
    const val ERROR_MESSAGE_NETWORK_NOTAVAILABLE = "Please enable your network connection"
    const val ERROR_MESSAGE_UNKNOWN_ERROR = "Something went wrong"
}