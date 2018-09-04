package id.aasumitro.submission004.ui.fragment

import android.widget.Toast

interface ListNavigator {

    fun onSuccess(isSuccess: Boolean, code: String?, message: String?)

    fun onLoading(isLoading: Boolean)

    fun initListAdapter()

    fun showToast(message: String): Toast?

    fun clearRecyclerView()

    fun checkNetworkConnection(): Boolean

}