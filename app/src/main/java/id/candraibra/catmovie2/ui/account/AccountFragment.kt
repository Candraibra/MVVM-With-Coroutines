package id.candraibra.catmovie2.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import id.candraibra.catmovie2.R
import id.candraibra.catmovie2.base.BaseFragment
import id.candraibra.catmovie2.utils.PrefHelper
import id.candraibra.catmovie2.utils.PrefKey
import id.candraibra.catmovie2.utils.Status
import kotlinx.android.synthetic.main.fragment_account.*
import timber.log.Timber

class AccountFragment : BaseFragment<AccountViewModel>() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun getViewModel(): Class<AccountViewModel> {
        return AccountViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        getAccountDetail()
    }

    private fun getAccountDetail() {
        viewModel.getAccountDetail().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let {account ->
                            setAccountId(account.id)
                            setView(account.name)
                        }
                    }
                    Status.ERROR -> {
                        Timber.d(String.format("%s %s", it.message, it.code))
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    private fun setAccountId(accountId: Int) {
        PrefHelper().setInt(PrefKey.ACCOUNT_ID, accountId)
    }

    private fun setView(name:String) {
        tvFullName.text = name
    }
}
