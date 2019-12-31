package de.fuchsch.email.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "folders",
    indices = [Index(value = ["account"], unique = true)],
    foreignKeys = [
    ForeignKey(entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["account"],
        onDelete = CASCADE)])
data class FolderEntity (
    @PrimaryKey val url: String,
    val name: String,
    val messageCount: Int,
    val hasUnreadMessages: Boolean,
    val account: Int
)
