package de.fuchsch.email.repository

import android.util.Log
import com.sun.mail.imap.IMAPFolder
import com.sun.mail.imap.IMAPStore
import de.fuchsch.email.database.entity.Account
import de.fuchsch.email.database.entity.IMAPProtocol
import de.fuchsch.email.database.entity.MessageEntity
import de.fuchsch.email.util.iterable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.mail.*
import javax.mail.internet.MimeMultipart

class MailService(private val session: Session) {

    private lateinit var store: IMAPStore

    suspend fun changeAccount(account: Account) = withContext(Dispatchers.IO) {
        store = when (account.settings.protocol) {
            IMAPProtocol.IMAPS -> session.getStore("imaps")
            IMAPProtocol.STARTTLS -> session.getStore("imaps")
            IMAPProtocol.IMAP -> session.getStore("imap")
        } as IMAPStore
        val settings = account.settings
        try {
            store.connect(settings.serverURL, settings.email, settings.password)
        } catch (e: AuthenticationFailedException) {
            Log.i(this::class.java.canonicalName, "${e.message}")
        }
    }

    suspend fun getRootFolders() = withContext(Dispatchers.IO) {
        store.defaultFolder.list().map { de.fuchsch.email.model.Folder.fromJavaMailFolder(it) }
    }

    private fun getMainContent(message: Message) = when (message.content) {
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

    suspend fun getMessages(url: URLName) = withContext(Dispatchers.IO) {
        val folder = store.getFolder(url) as IMAPFolder
        folder.open(Folder.READ_ONLY)
        folder.messages.map { message ->
            MessageEntity(
                message.subject,
                getMainContent(message),
                message.from.first().toString(),
                message.allRecipients.map { it.toString() },
                folder.getUID(message),
                folder.urlName.toString()
            )
        }
    }

    suspend fun messagesHaveBeenDeleted(url: URLName, uids: List<Long>) =
        withContext(Dispatchers.IO) {
            val folder = store.getFolder(url) as IMAPFolder
            folder.open(Folder.READ_ONLY)
            folder.getMessagesByUID(uids.toLongArray()).map { it == null }
        }

    suspend fun deleteMessage(folderName: String, messageNumber: Long) =
        withContext(Dispatchers.IO) {
            try {
                val folder = store.getFolder(folderName) as IMAPFolder
                folder.open(Folder.READ_WRITE)
                val message = folder.getMessageByUID(messageNumber)
                message.setFlag(Flags.Flag.DELETED, true)
                folder.expunge()
                return@withContext true
            } catch (e: IndexOutOfBoundsException) {
                Log.w(this::class.java.canonicalName, "${e.message}")
            } catch (e: FolderNotFoundException) {
                Log.w(this::class.java.canonicalName, "${e.message}")
            } catch (e: IllegalStateException) {
                Log.w(this::class.java.canonicalName, "${e.message}")
            } catch (e: MessagingException) {
                Log.w(this::class.java.canonicalName, "${e.message}")
            }
            return@withContext false
        }

}