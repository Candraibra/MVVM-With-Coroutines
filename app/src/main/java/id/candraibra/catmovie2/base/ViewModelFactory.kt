package id.candraibra.catmovie2.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.candraibra.catmovie2.repository.MyRepository
import id.candraibra.catmovie2.repository.network.RemoteSource
import id.candraibra.catmovie2.ui.account.AccountViewModel
import id.candraibra.catmovie2.ui.splashscreen.SplashScreenViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val remoteSource: RemoteSource) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
            return SplashScreenViewModel(MyRepository(remoteSource)) as T
        } else if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(MyRepository(remoteSource)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}

