package id.aasumitro.finalsubmission.ui.fragment.favorite

import android.widget.Toast

interface FavoriteNavigator {

    fun makeToast(message: String): Toast?

    fun onSuccess(isSuccess: Boolean, code: String?, message: String?)

    fun onLoading(isLoading: Boolean)

    fun initTeamListAdapter()

    fun initMatchListAdapter()

    fun clearRecyclerView()

}