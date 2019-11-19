package de.fuchsch.email.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import de.fuchsch.email.database.entity.Account
import de.fuchsch.email.model.Folder
import de.fuchsch.email.model.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.mail.AuthenticationFailedException
import javax.mail.Session

class MailRepository(private val session: Session) {

    val folders = MutableLiveData<List<Folder>>()

    val account = MutableLiveData<Account>()

    suspend fun getRootFolder() = withContext(Dispatchers.IO) {
        val imapStore = session.getStore("imaps")
        account.value?.let {
            try {
                imapStore.connect(it.settings.serverURL, it.settings.email, it.settings.password)
                val folderList = imapStore.defaultFolder
                    .list().asList().map { f -> Folder.fromJavaMailFolder(f) }
                withContext(Dispatchers.Main) {
                    folders.value = folderList
                }
            } catch (e: AuthenticationFailedException) {
                Log.i(this::class.java.canonicalName, "${e.message}")
            }
        }
    }

    suspend fun getMessages(folder: Folder): List<Message> = withContext(Dispatchers.IO) {
        val imapStore = session.getStore("imaps")
        account.value?.let {
            try {
                imapStore.connect(it.settings.serverURL, it.settings.email, it.settings.password)
                imapStore.getFolder(folder.name).messages.map { mail -> Message.fromMail(mail) }
            } catch (e: AuthenticationFailedException) {
                Log.i(this::class.java.canonicalName, "${e.message}")
                null
            }
        } ?: emptyList()
    }

}