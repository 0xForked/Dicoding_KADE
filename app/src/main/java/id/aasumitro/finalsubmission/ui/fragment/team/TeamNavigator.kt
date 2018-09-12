package id.aasumitro.finalsubmission.ui.fragment.team

import android.widget.Toast

interface TeamNavigator {

    fun toast(message: String): Toast?

    fun checkNetworkConnection(): Boolean

    fun onSuccess(isSuccess: Boolean, code: String?, message: String?)

    fun onLoading(isLoading: Boolean)

    fun initListAdapter()

    fun clearRecyclerView()

}