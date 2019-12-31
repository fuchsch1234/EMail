package de.fuchsch.email.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import de.fuchsch.email.database.entity.MessageEntity

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages")
    fun getAll(): LiveData<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE folder = :folder")
    fun getMessagesForFolder(folder: String): LiveData<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(message: MessageEntity)

}