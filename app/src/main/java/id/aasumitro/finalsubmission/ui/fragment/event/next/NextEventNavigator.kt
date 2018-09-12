package id.aasumitro.finalsubmission.ui.fragment.event.next

import android.widget.Toast

interface NextEventNavigator {

    fun showToast(message: String): Toast?

    fun checkNetworkConnection(): Boolean

    fun onSuccess(isSuccess: Boolean, code: String?, message: String?)

    fun onLoading(isLoading: Boolean)

    fun initListAdapter()

    fun clearRecyclerView()

}