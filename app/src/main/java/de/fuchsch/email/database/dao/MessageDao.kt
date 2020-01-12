package de.fuchsch.email.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.fuchsch.email.database.entity.MessageEntity

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages")
    fun getAll(): LiveData<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE folder = :folder")
    fun getMessagesForFolder(folder: String): LiveData<List<MessageEntity>>

    @Query("SELECT messageNumber FROM messages WHERE folder = :folder")
    suspend fun getIdsForFolder(folder: String): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(message: MessageEntity)

    @Query("DELETE FROM messages WHERE folder = :folder AND messageNumber = :uid")
    suspend fun delete(folder: String, uid: Long)

}