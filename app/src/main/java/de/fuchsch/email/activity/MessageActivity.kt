package de.fuchsch.email.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import de.fuchsch.email.R
import de.fuchsch.email.model.Message
import de.fuchsch.email.viewmodel.MessageViewModel

import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.content_message.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageActivity : AppCompatActivity() {

    companion object {

        val MESSAGE = "${MessageActivity::class.qualifiedName}.MESSAGE"

    }

    private val viewModel: MessageViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        viewModel.message.observe(this, Observer {
            it?.let { message ->
                SubjectLineText.setText(message.subject)
                SenderText.setText(message.sender)
                RecipientText.setText(message.recipients.first())
                MessageText.setText(message.message)
            }
        })

        intent.getParcelableExtra<Message>(MESSAGE).also { message ->
            viewModel.setMessage(message)
        }

        MessageDeleteButton.setOnClickListener { viewModel.deleteMessage() }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
