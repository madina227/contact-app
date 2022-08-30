package uz.gita.task.app

import android.app.Application
import timber.log.Timber
import uz.gita.task.BuildConfig
import uz.gita.task.data.source.local.AppDatabase

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppDatabase.init(this)
    }
}