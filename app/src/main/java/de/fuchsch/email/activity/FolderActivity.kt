package de.fuchsch.email.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import de.fuchsch.email.MessageAdapter
import de.fuchsch.email.R
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
        val adapter = MessageAdapter(this)
        MessageRecyclerView.adapter = adapter

        viewmodel.messages.observe(this, Observer {
            it?.let { messages -> adapter.messages = messages }
        })

        viewmodel.selectFolder(intent.getParcelableExtra(FOLDER))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
