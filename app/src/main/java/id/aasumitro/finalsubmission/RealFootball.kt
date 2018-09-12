package id.aasumitro.finalsubmission

import android.app.Application
import id.aasumitro.finalsubmission.data.source.local.RealFootballDb

class RealFootball : Application() {

    override fun onCreate() {
        super.onCreate()
        instanceDb()
    }

    fun instanceDb() = RealFootballDb.getInstance(this)

}