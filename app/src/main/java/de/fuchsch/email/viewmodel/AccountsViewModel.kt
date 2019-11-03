package de.fuchsch.email.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.fuchsch.email.database.entity.Account
import de.fuchsch.email.repository.AccountRepository
import kotlinx.coroutines.launch

class AccountsViewModel(private val repository: AccountRepository): ViewModel() {

    val accounts: LiveData<List<Account>> = repository.accounts

    fun insert(account: Account) = viewModelScope.launch {
        repository.saveAccount(account)
    }

}