package de.fuchsch.email.model

import android.os.Parcelable
import de.fuchsch.email.util.iterable
import kotlinx.android.parcel.Parcelize
import javax.mail.internet.MimeMultipart

@Parcelize
data class Message(
    val subject: String,
    val message: String,
    val sender: String,
    val recipients: List<String>
) : Parcelable {

    companion object {

        private fun getMainContent(message: javax.mail.Message) = when (message.content) {
            is String -> message.content as String
            is MimeMultipart -> {
                val mp = message.content as MimeMultipart
                mp.iterable()
                    .firstOrNull { it.contentType.startsWith("text/plain") && it.content is String }
                    ?.let { it.content as String }
                    ?: "Unknown content"
            }
            else -> {
                "Unsupported content type ${message.contentType}"
            }

        }

        fun fromMail(message: javax.mail.Message): Message {
            val content = getMainContent(message)
            return Message(
                message.subject,
                content,
                message.from.first().toString(),
                message.allRecipients.map { it.toString() }
            )
        }
    }
}