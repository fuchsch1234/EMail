package de.fuchsch.email.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fuchsch.email.model.Folder
import de.fuchsch.email.model.Message
import de.fuchsch.email.repository.MailRepository
import kotlinx.coroutines.launch

class MessageViewModel(private val repository: MailRepository): ViewModel() {

    private val messageLiveData = MutableLiveData<Message>()
    val message: LiveData<Message> = messageLiveData

    fun setMessage(message: Message) {
        messageLiveData.value = message
    }

    fun deleteMessage() {
        message.value?.let {
            viewModelScope.launch { repository.deleteMessage(it) }
        }
    }

    fun moveMessage(folder: Folder) {

    }

}