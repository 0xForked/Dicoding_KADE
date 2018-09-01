package id.aasumitro.submission003.ui.activity.splash

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import id.aasumitro.submission003.R
import id.aasumitro.submission003.data.source.remote.ApiClient
import id.aasumitro.submission003.ui.activity.main.MainActivity
import org.jetbrains.anko.startActivity
import id.aasumitro.submission003.util.DataHelper.isFirstLaunch
import id.aasumitro.submission003.util.visible
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.toast

class SplashActivity : AppCompatActivity(), SplashNavigation {

    private val mApiClient = ApiClient()

    private val mViewModel: SplashViewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mViewModel.initVM(this, mApiClient)
        mViewModel.startTask(isFirstLaunch(this), this)
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
        message?.let {
            toast(it)
        }
    }

}
