package id.aasumitro.finalsubmission.data.model

import com.google.gson.annotations.SerializedName

object DataResult {

    data class LeagueResult(@SerializedName("countrys")
                            var leagueList: List<League> = emptyList())

    // untuk list prev/next/detail team dari server
    // dia return {"events":[{}]}
    data class EventsResult(@SerializedName("events")
                           var matchList: List<Match> = emptyList())

    // untuk search team dari server
    // dia return {"event":[{},{},...]}
    data class EventResult(@SerializedName("event")
                            var matchList: List<Match> = emptyList())

    data class TeamResult(@SerializedName("teams")
                          var teamList: List<Team> = emptyList())

    // untuk list team dari server
    // dia return {"player":[{},{},...]}
    data class PlayerResult(@SerializedName("player")
                            var playerList: List<Player> = emptyList())

}