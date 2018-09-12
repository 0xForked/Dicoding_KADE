package id.aasumitro.finalsubmission.util

object GlobalConst {

    // Status
    const val MATCH_STATUS_FT = "FT"
    const val MATCH_STATUS_VS = "VS"
    const val PAST_MATCH = "PAST_MATCH"
    const val NEXT_MATCH = "NEXT_MATCH"
    const val SEARCH_MATCH = "SEARCH_MATCH"
    const val SEARCH_TEAM = "SEARCH_TEAM"
    var GLOBAL_MATCH_VALUE: String? = null
    var SEARCH_VALUE: String? = null

    // Error Code and Message
    const val ERROR_CODE_NETWORK_NOTAVAILABLE = "NETWORK_NOT_AVAILABLE"
    const val ERROR_CODE_UNKNOWN_ERROR = "UNKNOWN_ERROR"
    const val ERROR_CODE_EMPTY_VALUE = "EMPTY_VALUE"
    const val ERROR_CODE_DATA_NOTAVAILABLE = "DATA_NOT_AVAILABLE"

    const val ERROR_MESSAGE_NETWORK_NOTAVAILABLE = "Please enable your network connection"
    const val ERROR_MESSAGE_UNKNOWN_ERROR = "Something went wrong"
    const val ERROR_MESSAGE_EMPTY_FAVORITE_LIST = "You have no any favorite match yet"
    const val ERROR_MESSAGE_DATA_NOTAVAILABLE = "Match detail not available yet"

}