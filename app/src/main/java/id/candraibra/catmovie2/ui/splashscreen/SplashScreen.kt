package id.candraibra.catmovie2.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import id.candraibra.catmovie2.base.BaseActivity
import id.candraibra.catmovie2.databinding.ActivitySplashBinding
import id.candraibra.catmovie2.ui.navigation.NavigationActivity
import id.candraibra.catmovie2.utils.PrefHelper
import id.candraibra.catmovie2.utils.PrefKey
import id.candraibra.catmovie2.utils.Status
import timber.log.Timber

class SplashScreen : BaseActivity<SplashScreenViewModel>() {
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
    }

    private fun setupUi() {
        if (session().isNullOrEmpty()) {
            createToken()
        } else {
            Handler().postDelayed({
                Intent(this, NavigationActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }, 2000)
        }
    }

    private fun createToken() {
        viewModel.getToken().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { token ->
                            setToken(token.requestToken)
                            createSession()
                        }
                    }
                    Status.ERROR -> {
                        Timber.d(String.format("%s %s", it.message, it.code))
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    private fun createSession() {
        viewModel.getSession()
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { token ->
                                setSession(token.sessionId)
                                Intent(this, NavigationActivity::class.java).apply {
                                    startActivity(this)
                                    finish()
                                }
                            }
                        }
                        Status.ERROR -> {
                            Timber.d(String.format("%s %s", it.message, it.code))
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                        }
                    }
                }
            })
        viewModel.postValidateAndSession()
    }


    private fun setToken(token: String) {
        PrefHelper.setString(PrefKey.TOKEN, token)
    }

    private fun session(): String? {
        return PrefHelper.getString(PrefKey.SESSION)
    }

    private fun setSession(session: String) {
        PrefHelper.setString(PrefKey.SESSION, session)
    }

    override fun getViewModel(): Class<SplashScreenViewModel> {
        return SplashScreenViewModel::class.java
    }
}
