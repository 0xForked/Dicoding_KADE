package id.aasumitro.finalsubmission.ui.fragment.player

import android.arch.lifecycle.ViewModel
import id.aasumitro.finalsubmission.data.model.Player
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_CODE_NETWORK_NOTAVAILABLE
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_CODE_UNKNOWN_ERROR
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_MESSAGE_NETWORK_NOTAVAILABLE
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_MESSAGE_UNKNOWN_ERROR
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PlayerViewModel : ViewModel() {

    private var mNavigator: PlayerNavigator? = null
    private var mRepository: Repository? = null

    var mPlayerData = ArrayList<Player>()


    fun initVM(navigator: PlayerNavigator,
               repository: Repository) {
        this.mNavigator = navigator
        this.mRepository = repository
    }

    fun startTask(teamId: String?) {
        mNavigator?.onLoading(true)
        mNavigator?.clearRecyclerView()
        val isNetworkAvailable: Boolean =
                mNavigator?.checkNetworkConnection() as Boolean
        if (isNetworkAvailable) {
            mRepository.let { it ->
                it?.getPlayers(teamId?.toInt())
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe({ onSuccess ->
                            mPlayerData = onSuccess.playerList as ArrayList<Player>
                            mNavigator?.initListAdapter().let {
                                mNavigator?.onLoading(false)
                            }
                        }, { onError ->
                            onError.printStackTrace()
                            mNavigator?.onLoading(false)
                            mNavigator?.onSuccess(
                                    false,
                                    ERROR_CODE_UNKNOWN_ERROR,
                                    ERROR_MESSAGE_UNKNOWN_ERROR)
                        })
            }
        } else {
            mNavigator?.onSuccess(
                    false,
                    ERROR_CODE_NETWORK_NOTAVAILABLE,
                    ERROR_MESSAGE_NETWORK_NOTAVAILABLE)
            mNavigator?.onLoading(false)
        }

    }
}