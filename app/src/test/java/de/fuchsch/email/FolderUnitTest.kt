package de.fuchsch.email

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import de.fuchsch.email.model.Folder
import org.junit.Test

import org.junit.Assert.*

class FolderUnitTest {

    @Test
    fun `simple conversion works`() {
        val name = "Test"
        val messageCount = 5
        val unreadMessageCount = 3
        val mockedFolder = mock<javax.mail.Folder> {
            on { getName() } doReturn name
            on { getMessageCount() } doReturn messageCount
            on { getUnreadMessageCount() } doReturn unreadMessageCount
        }

        val folderUnderTest = Folder.fromJavaMailFolder(mockedFolder)
        assertEquals(name, folderUnderTest.name)
        assertEquals(messageCount, folderUnderTest.messageCount)
        assertTrue(folderUnderTest.hasUnreadMessages)
    }

}
