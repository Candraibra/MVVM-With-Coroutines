package id.candraibra.catmovie2.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.candraibra.catmovie2.repository.network.RemoteSource
import id.candraibra.catmovie2.repository.network.RetrofitBuilder

abstract class BaseFragment<VM : ViewModel> : Fragment() {
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