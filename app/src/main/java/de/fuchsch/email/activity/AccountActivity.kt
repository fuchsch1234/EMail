package de.fuchsch.email.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import de.fuchsch.email.Adapter
import de.fuchsch.email.R
import de.fuchsch.email.model.Folder
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

        val adapter = Adapter(
            this,
            R.layout.account_recyclerview_item,
            ::bindRecyclerViewHolder
        )
        { folder ->
            val intent = Intent(this, FolderActivity::class.java).apply {
                putExtra(FolderActivity.FOLDER, folder)
            }
            startActivity(intent)
        }

        FolderRecyclerView.layoutManager = LinearLayoutManager(this)
        FolderRecyclerView.adapter = adapter

        accountViewModel.folders.observe(this, Observer { folders ->
            folders?.let {
                adapter.items = it
            }

        })

        accountViewModel.select(intent.getParcelableExtra(MainActivity.ACCOUNT))

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun bindRecyclerViewHolder(itemView: View, folder: Folder, listener: (Folder) -> Unit) {
        val nameView: TextView = itemView.findViewById(R.id.FolderNameTextView)
        nameView.text = folder.name
        val messageView: TextView = itemView.findViewById(R.id.MessageCountTextView)
        messageView.text = folder.messageCount.toString()
        if (folder.hasUnreadMessages) {
            messageView.setTypeface(messageView.typeface, Typeface.BOLD)
        } else {
            messageView.setTypeface(messageView.typeface, Typeface.NORMAL)
        }
        val cardView: CardView = itemView.findViewById(R.id.FolderAdapterCardView)
        cardView.setOnClickListener { listener(folder) }
    }

}
