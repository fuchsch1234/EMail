package de.fuchsch.email.activity

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import de.fuchsch.email.R
import de.fuchsch.email.database.entity.Account
import de.fuchsch.email.database.entity.AccountSetting
import de.fuchsch.email.database.entity.IMAPProtocol
import de.fuchsch.email.viewmodel.AccountsViewModel

import kotlinx.android.synthetic.main.activity_add_account.*
import kotlinx.android.synthetic.main.content_add_account.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddAccountActivity : AppCompatActivity() {

    private val TAG = this::class.qualifiedName

    private val model: AccountsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.imap_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            IMAPSpinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.smtp_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            SMTPSpinner.adapter = adapter
        }

        AddAccountButton.setOnClickListener { addAccount() }
    }

    private fun addAccount() {
        val settings = AccountSetting(
            ServerURLEdit.text?.toString() ?: "",
            EMailEdit.text?.toString() ?: "",
            PasswordEdit.text?.toString() ?: "",
            IMAPProtocol.IMAPS
        )
        val account = Account(NameEdit.text?.toString() ?: "", settings)
        Log.i(TAG, "Adding account: $account")
        model.insert(account)
        setResult(0)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
