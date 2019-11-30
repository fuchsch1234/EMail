package de.fuchsch.email.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.fuchsch.email.R
import de.fuchsch.email.model.Message

import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.content_message.*

class MessageActivity : AppCompatActivity() {

    companion object {

        val MESSAGE = "${MessageActivity::class.qualifiedName}.MESSAGE"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        intent.getParcelableExtra<Message>(MESSAGE).also { message ->
            SubjectLineText.setText(message.subject)
            SenderText.setText(message.sender)
            RecipientText.setText(message.recipients.first())
            MessageText.setText(message.message)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
