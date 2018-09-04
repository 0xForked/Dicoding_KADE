package id.aasumitro.submission004.ui.activity.splash

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import id.aasumitro.submission004.R
import id.aasumitro.submission004.data.sources.EventRepository
import id.aasumitro.submission004.data.sources.remote.ApiClient
import id.aasumitro.submission004.ui.activity.main.MainActivity
import id.aasumitro.submission004.util.visible
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SplashActivity : AppCompatActivity(), SplashNavigator {

    private val mApiClient = ApiClient()
    private var mRepository: EventRepository? = null

    private val mViewModel: SplashViewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mRepository = EventRepository(this, mApiClient)
        mViewModel.initVM(this, mRepository as EventRepository)
        mViewModel.startTask()
    }

    override fun startMainActivity() {
        startActivity<MainActivity>()
        finish()
    }

    override fun loadingStatus(status: String?) {
        status?.let {
            act_splash_txt_loading_status.text = it
            act_splash_txt_loading_status.visible()
        }
    }

    override fun makeToast(message: String?) {
        message?.let { toast(it) }
    }

}