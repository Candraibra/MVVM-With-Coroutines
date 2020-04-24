package id.candraibra.catmovie2.base

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import coil.Coil
import coil.ImageLoader

import id.candraibra.catmovie2.BuildConfig
import timber.log.Timber


class MyApplication : Application() {
    companion object {
        lateinit var getInstance: MyApplication
        private lateinit var sharedPreferences: SharedPreferences
        private lateinit var ncSharedPreferences: SharedPreferences
    }


    init {
        getInstance = this
    }

    override fun onCreate() {
        super.onCreate()
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
//            Timber.plant(ReleaseTree())
        }
    }

    private fun initCoil() {
        Coil.setDefaultImageLoader {
            ImageLoader(this.applicationContext) {
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
        ncSharedPreferences =
            getSharedPreferences(MyApplication::class.java.simpleName + "_NC", Context.MODE_PRIVATE)
    }


    fun getSharedPreferences(): SharedPreferences {
        return sharedPreferences
    }

    fun getNcSharedPreferences(): SharedPreferences {
        return ncSharedPreferences
    }

}