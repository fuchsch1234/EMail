package de.fuchsch.email.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "messages",
    primaryKeys = ["messageNumber", "folder"],
    foreignKeys = [
    ForeignKey(entity = FolderEntity::class,
        parentColumns = ["url"],
        childColumns = ["folder"],
        onDelete = CASCADE)])
data class MessageEntity (
    val subject: String,
    val message: String,
    val sender: String,
    val recipients: List<String>,
    val messageNumber: Long,
    val folder: String
)
