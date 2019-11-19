package de.fuchsch.email.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.Transformations.switchMap
import de.fuchsch.email.model.Folder
import de.fuchsch.email.model.Message
import de.fuchsch.email.repository.MailRepository

class FolderViewModel(private val repository: MailRepository): ViewModel() {

    private val folder = MutableLiveData<Folder>()

    val messages: LiveData<List<Message>> = switchMap(folder) { f ->
        liveData {
            emit(repository.getMessages(f))
        }
    }

    fun selectFolder(folder: Folder) {
        this.folder.value = folder
    }

}