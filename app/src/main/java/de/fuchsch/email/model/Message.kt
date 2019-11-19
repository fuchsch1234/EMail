package de.fuchsch.email.model

data class Message(
    val subject: String,
    val message: String,
    val sender: String,
    val recipients: List<String>
) {

    companion object {

        fun fromMail(message: javax.mail.Message): Message {
            val content = when (message.contentType) {
                "text/plain" -> message.content as String
                else -> "Unsupported content type"
            }
            return Message(
                message.subject,
                content,
                message.from.first().toString(),
                message.allRecipients.map { it.toString() }
            )
        }
    }
}