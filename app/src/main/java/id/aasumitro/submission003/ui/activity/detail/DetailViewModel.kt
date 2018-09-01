package id.aasumitro.submission003.ui.activity.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import id.aasumitro.submission003.data.model.Favorite
import id.aasumitro.submission003.data.model.Match
import id.aasumitro.submission003.data.source.remote.ApiClient
import id.aasumitro.submission003.util.AppConst.ERROR_CODE_NETWORK_NOTAVAILABLE
import id.aasumitro.submission003.util.AppConst.ERROR_CODE_UNKNOWN_ERROR
import id.aasumitro.submission003.util.AppConst.ERROR_MESSAGE_NETWORK_NOTAVAILABLE
import id.aasumitro.submission003.util.AppConst.ERROR_MESSAGE_UNKNOWN_ERROR
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
import id.aasumitro.submission003.util.DataHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import id.aasumitro.submission003.util.ankoDB
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailViewModel : ViewModel() {

    private var mNavigator: DetailNavigator? = null
    private var mApiClient: ApiClient? = null

    private var mMatchDetail = MutableLiveData<Match>()

    private var mFavoriteEvent: Boolean = false

    fun initVM(navigation: DetailNavigator,
               apiClient: ApiClient) {
        this.mNavigator = navigation
        this.mApiClient = apiClient
    }

    fun startTask(eventId: String?) {
        val isNetworkAvailable = mNavigator?.checkNetworkConnection()
        if (isNetworkAvailable as Boolean) {
            detailEvent(eventId)
        } else {
            mNavigator?.onSuccess(
                    false,
                    ERROR_CODE_NETWORK_NOTAVAILABLE,
                    ERROR_MESSAGE_NETWORK_NOTAVAILABLE)
            mNavigator?.onLoading(false)
        }
    }

    private fun detailEvent(eventId: String?) {
        mNavigator?.onLoading(true)
        mApiClient
                ?.apiServices()
                ?.matchDetail(eventId?.toInt())
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ onSuccess ->
                    val dataResult = onSuccess.eventList[0]
                    mMatchDetail.value = dataResult
                    Log.d("DataReslut", dataResult.toString())
                    mNavigator?.onLoading(false)
                }, { onError ->
                    onError.printStackTrace()
                    onError.let {
                        mNavigator?.onSuccess(
                                false,
                                ERROR_CODE_UNKNOWN_ERROR,
                                ERROR_MESSAGE_UNKNOWN_ERROR)
                        mNavigator?.onLoading(false)
                        mNavigator?.onNextMatch()
                    }
                })
    }

    fun markAsFavorite(context: Context, match: Match?) {
        try {
            context.ankoDB.use {
                insert(TABLE_FAVORITE,
                        TABLE_FAVORITE_COLUMN_EVENT_ID to match?.eventId,
                        TABLE_FAVORITE_COLUMN_EVENT_DATE to match?.eventDate ,
                        TABLE_FAVORITE_COLUMN_EVENT_TIME to match?.eventTime,
                        TABLE_FAVORITE_COLUMN_ID_HOME_TEAM to match?.homeId ,
                        TABLE_FAVORITE_COLUMN_NAME_HOME_TEAM to match?.homeName,
                        TABLE_FAVORITE_COLUMN_SCORE_HOME_TEAM to match?.homeScore,
                        TABLE_FAVORITE_COLUMN_BANDAGE_HOME_TEAM to DataHelper
                                .getTeamBandage(context, match?.homeId),
                        TABLE_FAVORITE_COLUMN_ID_AWAY_TEAM to  match?.awayId,
                        TABLE_FAVORITE_COLUMN_NAME_AWAY_TEAM to match?.awayName,
                        TABLE_FAVORITE_COLUMN_SCORE_AWAY_TEAM to match?.awayScore,
                        TABLE_FAVORITE_COLUMN_BANDAGE_AWAY_TEAM to DataHelper
                                .getTeamBandage(context, match?.awayId)
                )
            }
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
        }
    }

    fun removeFromFavorite(context: Context, eventId: String?) {
        try {
            context.ankoDB.use {
                delete(TABLE_FAVORITE,
                        "(e_id = {id})",
                        "id" to eventId as String)
            }
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
        }
    }

    fun favoriteState(context: Context, eventId: String?) {
         try {
             context.ankoDB.use {
                 val result = select(TABLE_FAVORITE)
                         .whereArgs("(e_id = {id})",
                                 "id" to eventId as String)
                 val favorite = result.parseList(classParser<Favorite>())
                 mFavoriteEvent = !favorite.isEmpty()
             }
         } catch (e: SQLiteConstraintException) {
             e.printStackTrace()
         }
    }

    fun getDataResult(): LiveData<Match> = mMatchDetail

    fun getFavoriteState(): Boolean = mFavoriteEvent

}