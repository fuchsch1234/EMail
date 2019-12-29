package de.fuchsch.email.repository

import android.util.Log
import de.fuchsch.email.database.entity.Account
import de.fuchsch.email.database.entity.IMAPProtocol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.IllegalStateException
import java.lang.IndexOutOfBoundsException
import javax.mail.*

class MailService(private val session: Session) {

    private lateinit var store: Store

    suspend fun changeAccount(account: Account) = withContext(Dispatchers.IO) {
        store = when (account.settings.protocol) {
            IMAPProtocol.IMAPS -> session.getStore("imaps")
            IMAPProtocol.STARTTLS -> session.getStore("imaps")
            IMAPProtocol.IMAP -> session.getStore("imap")
        }
        val settings = account.settings
        try {
            store.connect(settings.serverURL, settings.email, settings.password)
        } catch (e: AuthenticationFailedException) {
            Log.i(this::class.java.canonicalName, "${e.message}")
        }
    }

    suspend fun getRootFolder() = withContext(Dispatchers.IO) {
        store.defaultFolder.list().asList()
    }

    suspend fun getMessages(folderName: String) = withContext(Dispatchers.IO) {
        val folder = store.getFolder(folderName)
        folder.open(Folder.READ_ONLY)
        folder.messages
    }

    suspend fun deleteMessage(folderName: String, messageNumber: Int) = withContext(Dispatchers.IO) {
        try {
            val folder = store.getFolder(folderName)
            folder.open(Folder.READ_WRITE)
            val message = folder.getMessage(messageNumber)
            message.setFlag(Flags.Flag.DELETED, true)
            folder.expunge()
        } catch (e: IndexOutOfBoundsException) {
            Log.w(this::class.java.canonicalName, "${e.message}")
        } catch (e: FolderNotFoundException) {
            Log.w(this::class.java.canonicalName, "${e.message}")
        } catch (e: IllegalStateException) {
            Log.w(this::class.java.canonicalName, "${e.message}")
        } catch (e: MessagingException) {
            Log.w(this::class.java.canonicalName, "${e.message}")
        }
    }
}