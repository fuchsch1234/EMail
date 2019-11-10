package de.fuchsch.email.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import de.fuchsch.email.FolderAdapter
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

        val adapter = FolderAdapter(this) {}
        FolderRecyclerView.layoutManager = LinearLayoutManager(this)
        FolderRecyclerView.adapter = adapter

        accountViewModel.folders.observe(this, Observer { folders ->
            folders?.let {
                adapter.folders = it
            }

        })

        accountViewModel.select(intent.getParcelableExtra(MainActivity.ACCOUNT))

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
