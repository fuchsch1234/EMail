package de.fuchsch.email.repository

import android.util.Log
import androidx.lifecycle.*
import de.fuchsch.email.database.dao.FolderDao
import de.fuchsch.email.database.dao.MessageDao
import de.fuchsch.email.database.entity.Account
import de.fuchsch.email.database.entity.FolderEntity
import de.fuchsch.email.model.Folder
import de.fuchsch.email.model.Message
import kotlinx.coroutines.*
import javax.mail.URLName

class MailRepository(private val mailService: MailService,
                     private val folderDao: FolderDao,
                     private val messageDao: MessageDao)
{

    private val accountId = MutableLiveData<Int>()

    val folders = accountId
        .switchMap { folderDao.getFoldersForAccount(it) }
        .map { list -> list.map { Folder(it.name, it.url, it.messageCount, it.hasUnreadMessages) } }

    private val folder = MutableLiveData<Folder>()

    private val url =
        folder.switchMap { folderDao.getUrlFromNameAndAccount(it.name, accountId.value ?: 0) }

    val messages = url
        .switchMap {
            Log.i(this::class.qualifiedName, "Getting messages for folder $it")
            messageDao.getMessagesForFolder(it) }
        .map { list ->
            list.map { Message(it.subject, it.message, it.sender, it.recipients, it.messageNumber.toInt()) } }

    init {
        url.observeForever { it?.let { GlobalScope.launch { refreshMessages(it) } } }
    }

    suspend fun changeAccount(account: Account) = coroutineScope {
        mailService.changeAccount(account)
        accountId.postValue(account.id)
    }

    fun changeFolder(f: Folder) = folder.postValue(f)

    suspend fun refreshFolderList() = coroutineScope {
        mailService.getRootFolders().map { folder ->
            val entity = FolderEntity(folder.url, folder.name, folder.messageCount,
                folder.hasUnreadMessages, accountId.value ?: 0)
            Log.i(this@MailRepository::class.qualifiedName, "Saving folder $entity to database")
            folderDao.save(entity)
        }
    }

    private suspend fun refreshMessages(url: String) = coroutineScope {
        awaitAll(
        async(Dispatchers.IO) {
            Log.i(this@MailRepository::class.qualifiedName, "Storing mail for folder $url")
            mailService.getMessages(URLName(url)).map {
                    messageDao.save(it)
            }
        },
        async(Dispatchers.IO) {
            val uids = messageDao.getIdsForFolder(url)
            val deleted = mailService.messagesHaveBeenDeleted(URLName(url), uids)
            uids.zip(deleted).forEach { (uid, delete) -> if (delete) messageDao.delete(url, uid) }
        }
        )
    }

    suspend fun deleteMessage(message: Message) = coroutineScope {
        folder.value?.let {folder ->
            mailService.deleteMessage(folder.name, message.messageNumber)
        }
    }

}