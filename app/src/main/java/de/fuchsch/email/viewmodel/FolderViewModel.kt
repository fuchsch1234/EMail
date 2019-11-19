package de.fuchsch.email.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.fuchsch.email.model.Folder
import de.fuchsch.email.model.Message
import de.fuchsch.email.repository.MailRepository

class FolderViewModel(private val repository: MailRepository): ViewModel() {

    private val folder = MutableLiveData<Folder>()

    val messages = MutableLiveData<List<Message>>()

    fun selectFolder(folder: Folder) {
        this.folder.value = folder
    }

}