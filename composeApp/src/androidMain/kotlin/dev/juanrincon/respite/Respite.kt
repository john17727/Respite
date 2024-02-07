package dev.juanrincon.respite

import android.app.Application
import di.initKoin
import org.koin.android.ext.koin.androidContext

class Respite : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@Respite)
        }
    }
}