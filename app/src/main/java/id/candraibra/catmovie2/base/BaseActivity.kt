package id.candraibra.catmovie2.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.candraibra.catmovie2.repository.network.RemoteSource
import id.candraibra.catmovie2.repository.network.RetrofitBuilder

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {
    lateinit var viewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(RemoteSource(RetrofitBuilder.getApi))
        ).get(getViewModel())
    }

    abstract fun getViewModel(): Class<VM>
}