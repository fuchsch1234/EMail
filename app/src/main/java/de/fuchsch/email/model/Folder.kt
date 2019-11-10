package de.fuchsch.email.model

import javax.mail.Folder

data class Folder (
    val name: String,
    val messageCount: Int,
    val hasUnreadMessages: Boolean
) {

    companion object {

        fun fromJavaMailFolder(folder: Folder) =
            Folder(
                folder.name,
                folder.messageCount,
                folder.unreadMessageCount > 0
            )

    }

}
