package de.fuchsch.email.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.fuchsch.email.database.entity.FolderEntity

@Dao
interface FolderDao {

    @Query("SELECT * FROM folders")
    fun getAll(): LiveData<List<FolderEntity>>

    @Query("SELECT * FROM folders WHERE account = :accountId")
    fun getFoldersForAccount(accountId: Int): LiveData<List<FolderEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(folder: FolderEntity)

}