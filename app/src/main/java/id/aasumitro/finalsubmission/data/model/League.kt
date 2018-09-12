package id.aasumitro.finalsubmission.data.model

import com.google.gson.annotations.SerializedName

data class League(@SerializedName("idLeague")
                  val uniqueId: String,
                  @SerializedName("strSport")
                  val type: String? = null,
                  @SerializedName("strLeague")
                  val name: String,
                  @SerializedName("strLeagueAlternate")
                  val altName: String? = null,
                  @SerializedName("intFormedYear")
                  val formedYear: String,
                  @SerializedName("strCountry")
                  var country: String)