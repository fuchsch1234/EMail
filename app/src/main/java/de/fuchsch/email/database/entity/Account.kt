package de.fuchsch.email.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account (
    @PrimaryKey val id: Int,
    val name: String,
    @Embedded val settings: AccountSetting
)
