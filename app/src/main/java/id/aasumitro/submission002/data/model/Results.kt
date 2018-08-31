package id.aasumitro.submission002.data.model

import com.google.gson.annotations.SerializedName

object Results {

    data class MatchResult(@SerializedName("events") var eventList: List<Match> = emptyList())

    data class TeamResult(@SerializedName("teams") var teamList: List<Team> = emptyList())

}
