package de.fuchsch.email.viewmodel

import androidx.lifecycle.*
import de.fuchsch.email.model.Folder
import de.fuchsch.email.repository.MailRepository

class FolderViewModel(private val repository: MailRepository): ViewModel() {

    val messages = repository.messages

    fun selectFolder(folder: Folder) = repository.changeFolder(folder)

}