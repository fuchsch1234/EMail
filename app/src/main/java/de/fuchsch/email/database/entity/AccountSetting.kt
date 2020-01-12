package de.fuchsch.email.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverter
import kotlinx.android.parcel.Parcelize

enum class IMAPProtocol {
    IMAP,
    IMAPS,
    STARTTLS
}

class IMAPProtocolConverter {

    @TypeConverter
    fun fromInt(value: Int?): IMAPProtocol? =
        IMAPProtocol.values().firstOrNull { it.ordinal == value }

    @TypeConverter
    fun imapProtocolToLong(protocol: IMAPProtocol?): Int? = protocol?.ordinal

}

@Parcelize
@Entity
data class AccountSetting(
    @ColumnInfo(name = "server_url") val serverURL: String,
    val email: String,
    val password: String,
    val protocol: IMAPProtocol
) : Parcelable
