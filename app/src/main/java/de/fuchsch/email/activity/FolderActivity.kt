package de.fuchsch.email.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import de.fuchsch.email.Adapter
import de.fuchsch.email.R
import de.fuchsch.email.model.Message
import de.fuchsch.email.viewmodel.FolderViewModel

import kotlinx.android.synthetic.main.activity_folder.*
import kotlinx.android.synthetic.main.content_folder.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FolderActivity : AppCompatActivity() {

    companion object {

        val FOLDER = "${FolderActivity::class.qualifiedName}.FOLDER"
    }

    private val viewmodel: FolderViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        MessageRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter(this,
            R.layout.folder_recyclerview_item,
            ::bindRecyclerViewHolder)
            {
                val intent = Intent(this, MessageActivity::class.java).apply {
                    putExtra(MessageActivity.MESSAGE, it)
                }
                startActivity(intent)
            }
        MessageRecyclerView.adapter = adapter

        viewmodel.messages.observe(this, Observer {
            it?.let { messages -> adapter.items = messages }
        })

        viewmodel.selectFolder(intent.getParcelableExtra(FOLDER))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun bindRecyclerViewHolder(itemView: View, message: Message, listener: (Message) -> Unit) {
        val subjectView: TextView = itemView.findViewById(R.id.SubjectLineTextView)
        subjectView.text = message.subject
        val messageView: TextView = itemView.findViewById(R.id.PreviewTextView)
        messageView.text = message.message.split("\n").first()
        val cardView: CardView = itemView.findViewById(R.id.MessageAdapterCardView)
        cardView.setOnClickListener { listener(message) }
    }

}
