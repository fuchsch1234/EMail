package de.fuchsch.email

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import de.fuchsch.email.activity.MessageActivity
import de.fuchsch.email.model.Message
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MessageActivityUnitTest {

    private val message = Message("Test subject",
        "Test message",
        "sender@example.com",
        listOf("recipient@example.com", "cc@example.com"))

    @Test
    fun `message is displayed`() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            MessageActivity::class.java)
        intent.putExtra(MessageActivity.MESSAGE, message)
        ActivityScenario.launch<MessageActivity>(intent)

        onView(withId(R.id.SubjectLineText)).check(matches(withText(message.subject)))
        onView(withId(R.id.SenderText)).check(matches(withText(message.sender)))
        onView(withId(R.id.RecipientText)).check(matches(withText(message.recipients.first())))
        onView(withId(R.id.MessageText)).check(matches(withText(message.message)))
    }

}