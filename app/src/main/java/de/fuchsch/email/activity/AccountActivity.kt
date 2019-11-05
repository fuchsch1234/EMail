package de.fuchsch.email.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import de.fuchsch.email.R
import de.fuchsch.email.viewmodel.AccountViewModel

import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.content_account.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountActivity : AppCompatActivity() {

    private val accountViewModel: AccountViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        accountViewModel.account.observe(this, Observer { account ->
            account?.let {
                MailText.text = "You've got no new mail for Account ${it.name}"
            }
        })

        accountViewModel.select(intent.getParcelableExtra(MainActivity.ACCOUNT))

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
