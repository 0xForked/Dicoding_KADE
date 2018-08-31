package id.aasumitro.submission002.ui.main.fragment

interface MainNavigator {

    fun onSuccess(isSuccess: Boolean, code: String?, message: String?)

    fun onLoading(isLoading: Boolean)

    fun initListAdapter()

    fun showToast(message: String)

    fun clearRecyclerView()

    fun checkNetworkConnection(): Boolean?

}