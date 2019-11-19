package de.fuchsch.email.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import javax.mail.Folder

@Parcelize
data class Folder (
    val name: String,
    val messageCount: Int,
    val hasUnreadMessages: Boolean
): Parcelable {

    companion object {

        fun fromJavaMailFolder(folder: Folder) =
            Folder(
                folder.name,
                folder.messageCount,
                folder.unreadMessageCount > 0
            )

    }

}
