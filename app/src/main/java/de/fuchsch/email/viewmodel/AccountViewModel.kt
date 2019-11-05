package de.fuchsch.email.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.fuchsch.email.database.entity.Account

class AccountViewModel: ViewModel() {

    private val mutableAccount = MutableLiveData<Account>()
    val account: LiveData<Account> = mutableAccount

    fun select(account: Account) {
        mutableAccount.value = account
    }

}