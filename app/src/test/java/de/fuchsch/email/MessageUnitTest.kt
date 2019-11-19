package de.fuchsch.email

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import de.fuchsch.email.model.Message
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.hasSize
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class MessageUnitTest {

    @Test
    fun `conversion from javamail message works`() {
        val mail = MimeMessage(Session.getDefaultInstance(emptyMap<String, String>().toProperties()))
        mail.sender = InternetAddress("sender@example.com")
        mail.addRecipient(javax.mail.Message.RecipientType.TO, InternetAddress("recipient@example.com"))
        mail.subject = "Test Subject"
        mail.setContent("Test Message", "text/plain")
//            val mail = mock<javax.mail.Message> {
//                on { allRecipients } doReturn arrayOf(InternetAddress("recipient@example.com"))
//                on { from } doReturn arrayOf(InternetAddress("sender@example.com"))
//                on { subject } doReturn "Test Subject"
//                on { toString() } doReturn "Test Message"
//            }

        val testObject = Message.fromMail(mail)
        assertEquals(testObject.subject, "Test Subject")
        assertThat(testObject.recipients, hasSize(1))
        assertThat(testObject.recipients, contains("recipient@example.com"))
        assertEquals(testObject.sender, "sender@example.com")
        assertEquals(testObject.message, "Test Message")
    }

}