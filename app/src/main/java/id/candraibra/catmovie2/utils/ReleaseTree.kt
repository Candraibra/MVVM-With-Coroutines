package id.candraibra.catmovie2.utils

import android.util.Log
import timber.log.Timber

class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            //Put ke firebase Crashlytics
            return
        }
    }
}

