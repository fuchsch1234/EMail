package de.fuchsch.email.repository

import androidx.lifecycle.LiveData
import de.fuchsch.email.database.dao.AccountDao
import de.fuchsch.email.database.entity.Account

class AccountRepository(private val accountDao: AccountDao) {

    val accounts: LiveData<List<Account>> = accountDao.getAll()

    suspend fun saveAccount(account: Account) {
        accountDao.save(account)
    }

}