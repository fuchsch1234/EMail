package de.fuchsch.email.repository

import androidx.lifecycle.MutableLiveData
import de.fuchsch.email.database.entity.Account
import de.fuchsch.email.model.Folder
import de.fuchsch.email.model.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class MailRepository(private val mailService: MailService) {

    val folders = MutableLiveData<List<Folder>>()

    val account = MutableLiveData<Account>()

    suspend fun changeAccount(account: Account) = coroutineScope {
        mailService.changeAccount(account)
    }

    suspend fun getRootFolder() = coroutineScope {
        val folderList = mailService.getRootFolder().map { f -> Folder.fromJavaMailFolder(f) }
        withContext(Dispatchers.Main) {
            folders.value = folderList
        }
    }

    suspend fun getMessages(folder: Folder): List<Message> = coroutineScope {
        mailService.getMessages(folder.name).map { Message.fromMail(it) }
    }

    suspend fun deleteMessage(message: Message) = coroutineScope {
        mailService.deleteMessage(message.folder.name, message.messageNumber)
    }

}