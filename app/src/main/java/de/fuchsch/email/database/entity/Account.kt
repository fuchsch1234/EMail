package de.fuchsch.email.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account (
    val name: String,
    @Embedded val settings: AccountSetting,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
