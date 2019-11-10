package de.fuchsch.email.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fuchsch.email.database.entity.Account
import de.fuchsch.email.model.Folder
import de.fuchsch.email.repository.MailRepository
import kotlinx.coroutines.launch

class AccountViewModel(private val mailRepository: MailRepository): ViewModel() {

    val account: LiveData<Account> = mailRepository.account

    val folders: LiveData<List<Folder>> = mailRepository.folders

    fun select(account: Account) {
        mailRepository.account.value = account
        viewModelScope.launch { mailRepository.getRootFolder() }
    }

}