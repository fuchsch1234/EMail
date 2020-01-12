package de.fuchsch.email.database.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Account(
    val name: String,
    @Embedded val settings: AccountSetting,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable
