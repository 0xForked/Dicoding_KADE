package id.aasumitro.finalsubmission.ui.fragment.player

import android.widget.Toast

interface PlayerNavigator {

    fun showToast(message: String): Toast?

    fun checkNetworkConnection(): Boolean

    fun onSuccess(isSuccess: Boolean, code: String?, message: String?)

    fun onLoading(isLoading: Boolean)

    fun initListAdapter()

    fun clearRecyclerView()

}