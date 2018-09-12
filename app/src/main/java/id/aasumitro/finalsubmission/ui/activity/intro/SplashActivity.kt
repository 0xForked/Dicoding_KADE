package id.aasumitro.finalsubmission.ui.activity.intro

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.data.source.remote.ApiClient
import id.aasumitro.finalsubmission.ui.activity.main.MainActivity
import id.aasumitro.finalsubmission.util.visible
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity(), SplashNavigator {

    private var mRepository: Repository? = null

    private val mViewModel: SplashViewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mRepository = Repository(this, ApiClient())
        mViewModel.initVM(this, mRepository as Repository)
        mViewModel.startTask()
    }

    override fun startMain() {
        startActivity<MainActivity>()
        finish()
    }

    override fun loadingStatus(status: String?) {
        status?.let {
            act_splash_txt_loading_status.text = it
            act_splash_txt_loading_status.visible()
        }
    }

    override fun toast(message: String?): Toast? = message?.let { toast(it) }

}
