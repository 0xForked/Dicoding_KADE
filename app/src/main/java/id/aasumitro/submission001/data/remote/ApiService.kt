package id.aasumitro.submission001.data.remote

import id.aasumitro.submission001.data.model.FMData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Agus Adhi Sumitro on 28/08/2018.
 * https://aasumitro.id
 * aasumitro@gmail.com
 */

interface ApiService {

    @GET("everything")
    fun everythingNews(
            @Query("sources") sources: String,
            @Query("apiKey") key: String
    ): Observable<FMData>

}