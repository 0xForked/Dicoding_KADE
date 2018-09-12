package id.aasumitro.finalsubmission.ui.fragment.event.past

import android.widget.Toast

interface PastEventNavigator {

    fun showToast(message: String): Toast?

    fun checkNetworkConnection(): Boolean

    fun onSuccess(isSuccess: Boolean, code: String?, message: String?)

    fun onLoading(isLoading: Boolean)

    fun initListAdapter()

    fun clearRecyclerView()

}