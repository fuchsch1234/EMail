package de.fuchsch.email

import de.fuchsch.email.model.Message
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.hasSize
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

class MessageUnitTest {

    private lateinit var mail: MimeMessage

    @Before
    fun setUp() {
        mail = MimeMessage(Session.getDefaultInstance(emptyMap<String, String>().toProperties()))
            .apply {
                sender = InternetAddress("sender@example.com")
                addRecipient(
                    javax.mail.Message.RecipientType.TO,
                    InternetAddress("recipient@example.com")
                )
                subject = "Test Subject"
            }
    }

    @Test
    fun `conversion from simple javamail message works`() {
        mail.setContent("Test Message", "text/plain")
        mail.saveChanges()

        val testObject = Message.fromMail(mail)
        assertEquals(testObject.subject, "Test Subject")
        assertThat(testObject.recipients, hasSize(1))
        assertThat(testObject.recipients, contains("recipient@example.com"))
        assertEquals(testObject.sender, "sender@example.com")
        assertEquals(testObject.message, "Test Message")
    }

    @Test
    fun `conversion of multipart javamail message works`() {
        val mp = MimeMultipart()
        mp.addBodyPart(MimeBodyPart().apply { setContent("Test Message", "text/plain") })
        mp.addBodyPart(MimeBodyPart().apply { setContent("Test HTML Message", "text/html") })
        mail.setContent(mp, "multipart/alternative")
        mail.saveChanges()

        val testObject = Message.fromMail(mail)
        assertEquals(testObject.subject, "Test Subject")
        assertThat(testObject.recipients, hasSize(1))
        assertThat(testObject.recipients, contains("recipient@example.com"))
        assertEquals(testObject.sender, "sender@example.com")
        assertEquals(testObject.message, "Test Message")
    }

    @Test
    fun `conversion of multipart javamail message without plaintext fails`() {
        val mp = MimeMultipart()
        mp.addBodyPart(MimeBodyPart().apply { setContent("Test HTML Message", "text/html") })
        mp.addBodyPart(MimeBodyPart().apply { setContent("Another HTML Message", "text/html") })
        mail.setContent(mp, "multipart/alternative")
        mail.saveChanges()

        val testObject = Message.fromMail(mail)
        assertEquals(testObject.subject, "Test Subject")
        assertThat(testObject.recipients, hasSize(1))
        assertThat(testObject.recipients, contains("recipient@example.com"))
        assertEquals(testObject.sender, "sender@example.com")
        assertEquals(testObject.message, "Unknown content")
    }
}