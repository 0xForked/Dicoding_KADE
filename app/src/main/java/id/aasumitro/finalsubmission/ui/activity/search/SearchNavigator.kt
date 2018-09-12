package id.aasumitro.finalsubmission.ui.activity.search

interface SearchNavigator {

    fun onLoading(isLoading: Boolean)

    fun initListAdapter()

    fun clearRecyclerView()

}