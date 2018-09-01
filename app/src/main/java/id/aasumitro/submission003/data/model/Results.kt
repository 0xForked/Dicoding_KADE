package id.aasumitro.submission003.data.model

import com.google.gson.annotations.SerializedName

object Results {

    data class MatchResult(@SerializedName("events") var eventList: List<Match> = emptyList())
    data class TeamResult(@SerializedName("teams") var teamList: List<Team> = emptyList())

    //for anko
    data class CountData(val count: Int)
    data class BandageData(val bandage: String)
    data class StadiumData(val stadium: String)
}
