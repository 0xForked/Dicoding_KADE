package id.aasumitro.submission004

import android.app.Application
import id.aasumitro.submission004.data.sources.local.DBEvent

class RealFootball : Application() {

    override fun onCreate() {
        super.onCreate()
        dbInstance()
    }

    fun dbInstance() = DBEvent.getInstance(this)

}