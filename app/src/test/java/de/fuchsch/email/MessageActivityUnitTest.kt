package de.fuchsch.email

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.nhaarman.mockitokotlin2.*
import de.fuchsch.email.activity.MessageActivity
import de.fuchsch.email.model.Folder
import de.fuchsch.email.model.Message
import de.fuchsch.email.viewmodel.MessageViewModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.mock.declareMock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MessageActivityUnitTest : KoinTest {

    private val message = Message(
        "Test subject",
        "Test message",
        "sender@example.com",
        listOf("recipient@example.com", "cc@example.com"),
        1
    )

    private lateinit var viewModel: MessageViewModel

    @Before
    fun setUp() {
        declareMock<MessageViewModel> {
            viewModel = this
            given(message).willReturn { MutableLiveData(this@MessageActivityUnitTest.message) }
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `message is displayed`() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            MessageActivity::class.java
        )
        intent.putExtra(MessageActivity.MESSAGE, message)
        ActivityScenario.launch<MessageActivity>(intent)

        onView(withId(R.id.SubjectLineText)).check(matches(withText(message.subject)))
        onView(withId(R.id.SenderText)).check(matches(withText(message.sender)))
        onView(withId(R.id.RecipientText)).check(matches(withText(message.recipients.first())))
        onView(withId(R.id.MessageText)).check(matches(withText(message.message)))
    }

    @Test
    fun `message can be moved`() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            MessageActivity::class.java
        )
        intent.putExtra(MessageActivity.MESSAGE, message)
        ActivityScenario.launch<MessageActivity>(intent)

        onView(withId(R.id.MessageMoveButton)).perform(click())
        verify(viewModel, times(1)).moveMessage(any())
    }

    @Test
    fun `message can be deleted`() {
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            MessageActivity::class.java
        )
        intent.putExtra(MessageActivity.MESSAGE, message)
        ActivityScenario.launch<MessageActivity>(intent)

        onView(withId(R.id.MessageDeleteButton)).perform(click())
        verify(viewModel, times(1)).deleteMessage()
    }
}