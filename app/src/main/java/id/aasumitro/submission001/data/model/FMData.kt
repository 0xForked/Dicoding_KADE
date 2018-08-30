package id.aasumitro.submission001.data.model

import com.google.gson.annotations.SerializedName

data class FMData(
        @SerializedName("status") var status: String = "",
        @SerializedName("totalResults") var total: String = "",
        @SerializedName("code") var code: String? = null,
        @SerializedName("message") var message: String? = null,
        @SerializedName("articles") var articles: List<ResultData> = emptyList())



