package de.fuchsch.email.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "folders",
    primaryKeys = ["name", "account"],
    foreignKeys = [
    ForeignKey(entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["account"],
        onDelete = CASCADE)])
data class FolderEntity (
    val name: String,
    val messageCount: Int,
    val hasUnreadMessages: Boolean,
    val account: Int
)
