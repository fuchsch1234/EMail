package de.fuchsch.email.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.fuchsch.email.database.entity.Account

@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    fun getAll(): LiveData<List<Account>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(account: Account)

}