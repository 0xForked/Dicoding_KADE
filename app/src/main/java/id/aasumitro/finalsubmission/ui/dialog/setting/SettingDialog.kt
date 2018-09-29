package id.aasumitro.finalsubmission.ui.dialog.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v4.os.ResultReceiver
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.gson.JsonArray
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.data.model.League
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.KEY_LEAGUE_ALT_NAME
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.KEY_LEAGUE_ID
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.KEY_LEAGUE_NAME
import id.aasumitro.finalsubmission.ui.activity.main.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_setting.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.progressDialog
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SettingDialog : Activity() {

    private var mRepository: Repository? = null
    private var mLeague = ArrayList<League>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_setting)
        mRepository = Repository(this)
        leagueFromServer()
    }

    private fun initSpinner() {
        val spinner = setting_spinner as Spinner
        val listLeagueName = ArrayList<String>()

        // ini data dari json assets
        // val listJson = leagueFromRaw()
        // (0 until listJson?.size() as Int)
        //        .mapTo(listLeagueName) {
        //            listJson[it]
        //                    .asJsonObject
        //                    .get("strLeague")
        //                    .toString()
        //                    .replace("\"", "")
        //        }

        // ini data dari server
        val listLeague = mLeague
        (0 until listLeague.size)
                .mapTo(listLeagueName) {
            listLeague[it].name
        }

        val adapter = ArrayAdapter<String>(this,
                R.layout.item_spinner_dropdown, listLeagueName)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        setting_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, pos: Int, id: Long) {
                saveLeagueData(spinner.selectedItemPosition,
                        spinner.selectedItem.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>) { }
        }
        spinner.setSelection(spinnerSelectedItem(spinner, leagueNow()))
    }

    private fun spinnerSelectedItem(spinner: Spinner, spinnerSelected: String?): Int {
        return (0 until spinner.count).firstOrNull {
            spinner.getItemAtPosition(it)
                    .toString()
                    .equals(spinnerSelected, ignoreCase=true)
        } ?: 0
    }

    private fun saveLeagueData(leagueIndex: Int, leagueName: String) {
        val listLeague = mLeague
        val leagueListData = listLeague[leagueIndex]
        val id = leagueListData.uniqueId
        val name = leagueListData.name
        val altName = leagueListData.altName ?: "none"
        btn_setting_save_league.setOnClickListener {
            if (leagueName != leagueNow()) {
                mRepository?.setPrefs(KEY_LEAGUE_ID, id)
                mRepository?.setPrefs(KEY_LEAGUE_NAME, name)
                mRepository?.setPrefs(KEY_LEAGUE_ALT_NAME, altName)
                syncTeamData(leagueName)
            } else {
                toast("Selected league ${leagueNow() as String}")
            }
        }
    }

    private fun syncTeamData(leagueName: String) {
        val dialog =
                indeterminateProgressDialog(message = "Please wait a bit…", title = "Sync data")
        dialog.setCancelable(false)
        dialog.show()
        mRepository.let { it ->
            it?.getRemoteTeams(leagueName)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ onSuccess ->
                        val mRemoteData =
                                onSuccess.teamList as ArrayList<Team>
                        it.updateTeams(mRemoteData)
                        dialog.dismiss()
                        refresh()
                    }, { onError ->
                        onError.printStackTrace()
                        dialog.dismiss()
                        toast("Failed fetching data from server!")
                    })
        }
    }

    private fun leagueFromServer() {
        val dialog =
                indeterminateProgressDialog(message = "Please wait a bit…",
                        title = "Fetching league")
        dialog.setCancelable(false)
        dialog.show()
        mRepository.let {
            it?.getRemoteLeague()
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.subscribe({ onSuccess ->
                        val mRemoteData =
                                onSuccess.leagueList as ArrayList<League>
                        mLeague = mRemoteData
                        dialog.dismiss()
                        initSpinner()
                    }, { onError ->
                        onError.printStackTrace()
                        dialog.dismiss()
                        toast("Failed fetching data from server!")
                    })
        }
    }

    //private fun leagueFromRaw(): JsonArray? {
    //    val leagueObject = mRepository?.getLeague()
    //    return leagueObject?.get("leagues")?.asJsonArray
    //}

    private fun leagueNow(): String? =
            mRepository?.getPrefs(KEY_LEAGUE_NAME)

    @SuppressLint("RestrictedApi")
    private fun refresh() {
        finish()
        (intent.getParcelableExtra("finisher")
                as ResultReceiver)
                .send(1, Bundle())
        startActivity<MainActivity>()
    }

}