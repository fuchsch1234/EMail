package de.fuchsch.email.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.fuchsch.email.model.Folder
import de.fuchsch.email.model.Message
import de.fuchsch.email.repository.MailRepository

class MessageViewModel(private val repository: MailRepository): ViewModel() {

    private val messageLiveData = MutableLiveData<Message>()
    val message: LiveData<Message> = messageLiveData

    fun setMessage(message: Message) {
        messageLiveData.value = message
    }

    fun deleteMessage() {
    }

    fun moveMessage(folder: Folder) {

    }

}