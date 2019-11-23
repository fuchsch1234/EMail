package de.fuchsch.email.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import de.fuchsch.email.Adapter
import de.fuchsch.email.R
import de.fuchsch.email.database.entity.Account
import de.fuchsch.email.viewmodel.AccountsViewModel
import kotlinx.android.synthetic.main.accounts_recyclerview_item.view.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {

        val ACCOUNT = "${this::class.java.canonicalName}.ACCOUNT"

    }

    private val accountsModel: AccountsViewModel by viewModel()

    private val ADD_ACCOUNT_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val adapter = Adapter(this, this::bindRecyclerViewHolder, this::selectAccount)
        MainRecyclerView.adapter = adapter
        MainRecyclerView.layoutManager = LinearLayoutManager(this)
        accountsModel.accounts.observe(this, Observer { accounts ->
            accounts?.let { adapter.items = it }
        })

        fab.setOnClickListener { addAccount() }
    }

    private fun addAccount() {
        val intent = Intent(this, AddAccountActivity::class.java)
        startActivityForResult(intent, ADD_ACCOUNT_REQUEST)
    }

    private fun selectAccount(account: Account) {
        val intent = Intent(this, AccountActivity::class.java).apply {
            putExtra(ACCOUNT, account)
        }
        startActivity(intent)
    }

    private fun bindRecyclerViewHolder(itemView: View, account: Account, listener: (Account) -> Unit) {
        val card: CardView = itemView.findViewById(R.id.AccountCardView)
        card.AccountNameTextView.text = account.name
        card.setOnClickListener { listener(account) }
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
