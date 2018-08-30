package id.aasumitro.submission001.util

/**
 * Created by Agus Adhi Sumitro on 28/08/2018.
 * https://aasumitro.id
 * aasumitro@gmail.com
 */

object AppConst {

    const val API_URL = "https://newsapi.org/v2/"
    const val API_KEY = "032c8af8c7c3496c813f1effc2a9b370"
    const val SOURCE_KEY = "bbc-news"

    const val MAX_WIDTH = 1024
    const val MAX_HEIGHT = 768
    var SIZE = Math.ceil(Math.sqrt((MAX_WIDTH * MAX_HEIGHT).toDouble())).toInt()

}