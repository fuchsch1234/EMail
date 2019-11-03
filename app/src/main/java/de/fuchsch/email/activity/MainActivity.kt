package de.fuchsch.email.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import de.fuchsch.email.AccountAdapter
import de.fuchsch.email.R
import de.fuchsch.email.viewmodel.AccountsViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val model: AccountsViewModel by viewModel()

    private val ADD_ACCOUNT_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val adapter = AccountAdapter(this)
        MainRecyclerView.adapter = adapter
        MainRecyclerView.layoutManager = LinearLayoutManager(this)
        model.accounts.observe(this, Observer { accounts ->
            accounts?.let { adapter.setAccounts(it) }
        })

        fab.setOnClickListener { addAccount() }
    }

    private fun addAccount() {
        val intent = Intent(this, AddAccountActivity::class.java)
        startActivityForResult(intent, ADD_ACCOUNT_REQUEST)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
