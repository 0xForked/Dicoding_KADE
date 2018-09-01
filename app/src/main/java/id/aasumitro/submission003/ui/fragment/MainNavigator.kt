package id.aasumitro.submission003.ui.fragment

interface MainNavigator {

    fun onSuccess(isSuccess: Boolean, code: String?, message: String?)

    fun onLoading(isLoading: Boolean)

    fun initListAdapter()

    fun showToast(message: String)

    fun clearRecyclerView()

    fun checkNetworkConnection(): Boolean?

}