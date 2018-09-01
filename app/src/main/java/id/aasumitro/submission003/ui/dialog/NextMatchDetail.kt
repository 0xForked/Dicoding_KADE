package id.aasumitro.submission003.ui.dialog

import android.app.Activity
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import id.aasumitro.submission003.R
import id.aasumitro.submission003.data.model.Favorite
import id.aasumitro.submission003.data.model.Match
import id.aasumitro.submission003.data.source.remote.ApiClient
import id.aasumitro.submission003.util.AppConst
import id.aasumitro.submission003.util.DataHelper
import id.aasumitro.submission003.util.ankoDB
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_next_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class NextMatchDetail : Activity() {

    private var mApiClient = ApiClient()
    private var isFavorite: Boolean = false
    private var mEventId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_next_match)
        mEventId = intent.getStringExtra(AppConst.INTENT_DATA_EVENT_ID)
        favoriteState()
        favoriteStatus()
        favoriteOnPressed()
    }

    private fun favoriteOnPressed() {
        dialog_icon_favorite.setOnClickListener {
            if (isFavorite) {
                removeFromFavorite(this, mEventId)
            } else {
                mApiClient
                        .apiServices()
                        ?.matchDetail(mEventId?.toInt())
                        ?.subscribeOn(Schedulers.newThread())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe({ onSuccess ->
                            val dataResult = onSuccess.eventList[0]
                            markAsFavorite(this, dataResult)

                        }, { onError ->
                            onError.printStackTrace()
                        })
            }
            isFavorite = !isFavorite
            favoriteStatus()
        }
    }

    private fun favoriteStatus() {
        if (isFavorite) {
            dialog_icon_favorite.setImageDrawable( ContextCompat
                    .getDrawable(this, R.drawable.ic_favorite))
        } else {
            dialog_icon_favorite.setImageDrawable(ContextCompat
                    .getDrawable(this, R.drawable.ic_favorite_border))
        }
    }

    private fun favoriteState() {
        try {
            ankoDB.use {
                val result = select(AppConst.TABLE_FAVORITE)
                        .whereArgs("(e_id = {id})",
                                "id" to mEventId as String)
                val favorite = result.parseList(classParser<Favorite>())
                if (!favorite.isEmpty()) isFavorite = true
            }
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
        }
    }

    private fun markAsFavorite(context: Context, match: Match?) {
        try {
            context.ankoDB.use {
                insert(AppConst.TABLE_FAVORITE,
                        AppConst.TABLE_FAVORITE_COLUMN_EVENT_ID to match?.eventId,
                        AppConst.TABLE_FAVORITE_COLUMN_EVENT_DATE to match?.eventDate ,
                        AppConst.TABLE_FAVORITE_COLUMN_EVENT_TIME to match?.eventTime,
                        AppConst.TABLE_FAVORITE_COLUMN_ID_HOME_TEAM to match?.homeId ,
                        AppConst.TABLE_FAVORITE_COLUMN_NAME_HOME_TEAM to match?.homeName,
                        AppConst.TABLE_FAVORITE_COLUMN_SCORE_HOME_TEAM to match?.homeScore,
                        AppConst.TABLE_FAVORITE_COLUMN_BANDAGE_HOME_TEAM to DataHelper
                                .getTeamBandage(context, match?.homeId),
                        AppConst.TABLE_FAVORITE_COLUMN_ID_AWAY_TEAM to  match?.awayId,
                        AppConst.TABLE_FAVORITE_COLUMN_NAME_AWAY_TEAM to match?.awayName,
                        AppConst.TABLE_FAVORITE_COLUMN_SCORE_AWAY_TEAM to match?.awayScore,
                        AppConst.TABLE_FAVORITE_COLUMN_BANDAGE_AWAY_TEAM to DataHelper
                                .getTeamBandage(context, match?.awayId)
                )
            }
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
        }
    }

    private fun removeFromFavorite(context: Context, eventId: String?) {
        try {
            context.ankoDB.use {
                delete(AppConst.TABLE_FAVORITE,
                        "(e_id = {id})",
                        "id" to eventId as String)
            }
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
        }
    }
}