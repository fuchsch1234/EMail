package de.fuchsch.email.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import de.fuchsch.email.database.dao.FolderDao
import de.fuchsch.email.database.dao.MessageDao
import de.fuchsch.email.database.entity.Account
import de.fuchsch.email.database.entity.FolderEntity
import de.fuchsch.email.model.Folder
import de.fuchsch.email.model.Message
import kotlinx.coroutines.coroutineScope

class MailRepository(private val mailService: MailService,
                     private val folderDao: FolderDao,
                     private val messageDao: MessageDao)
{

    private val accountId = MutableLiveData<Int>()

    val folders = accountId
        .switchMap { folderDao.getFoldersForAccount(it) }
        .map { list -> list.map { Folder(it.name, it.messageCount, it.hasUnreadMessages) } }

    private val folder = MutableLiveData<Folder>()

    val messages = folder
        .switchMap {
            val id = accountId.value ?: 0
            val url = folderDao.getUrlFromNameAndAccount(it.name, id)
            messageDao.getMessagesForFolder(url)
        }
        .map { list -> list.map { Message(it.subject, it.message, it.sender, it.recipients, it.messageNumber) } }

    suspend fun changeAccount(account: Account) = coroutineScope {
        mailService.changeAccount(account)
        accountId.postValue(account.id)
    }

    fun changeFolder(f: Folder) = folder.postValue(f)

    suspend fun getRootFolder() = coroutineScope {
        mailService.getRootFolder().map { f ->
            val folder = Folder.fromJavaMailFolder(f)
            val entity = FolderEntity(f.urlName.toString(),
                folder.name, folder.messageCount,
                folder.hasUnreadMessages, accountId.value ?: 0)
            folderDao.save(entity)
        }
    }

    suspend fun getMessages(folder: Folder): List<Message> = coroutineScope {
        mailService.getMessages(folder.name).map { Message.fromMail(it) }
    }

    suspend fun deleteMessage(message: Message) = coroutineScope {
        folder.value?.let {folder ->
            mailService.deleteMessage(folder.name, message.messageNumber)
        }
    }

}