package id.candraibra.catmovie2.base

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import coil.Coil
import coil.ImageLoader

import id.candraibra.catmovie2.BuildConfig
import id.candraibra.catmovie2.utils.ReleaseTree
import timber.log.Timber


class MyApplication : Application() {

    companion object {
        lateinit var getInstance: MyApplication
        private lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        getInstance = this
        setupSharedPreferences()
        initCoil()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return String.format(
                        "%s, Line: %s, Method: %s",
                        super.createStackElementTag(element),
                        element.lineNumber,
                        element.methodName
                    )
                }
            })
        } else {
           Timber.plant(ReleaseTree())
        }
    }

    private fun initCoil() {
        Coil.setDefaultImageLoader {
            ImageLoader(this) {
                crossfade(true)
                //if yuu want to enable cache
//                okHttpClient {
//                    OkHttpClient.Builder()
//                        .cache(CoilUtils.createDefaultCache(context))
//                        .build()
//                }
            }
        }
    }


    private fun setupSharedPreferences() {
        sharedPreferences =
            getSharedPreferences(MyApplication::class.java.simpleName, Context.MODE_PRIVATE)
    }


    fun getSharedPreferences(): SharedPreferences {
        return sharedPreferences
    }

}