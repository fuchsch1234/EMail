package de.fuchsch.email

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.nhaarman.mockitokotlin2.*
import de.fuchsch.email.activity.FolderActivity
import de.fuchsch.email.activity.MessageActivity
import de.fuchsch.email.model.Folder
import de.fuchsch.email.model.Message
import de.fuchsch.email.util.RecyclerViewItemCountAssertion.Companion.hasItemCount
import de.fuchsch.email.viewmodel.FolderViewModel
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.mock.declareMock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class FolderActivityUnitTest : KoinTest {

    private lateinit var viewmodel: FolderViewModel

    private val message = Message("Test Subject", "Test Message", "", emptyList(), 1)

    @get:Rule
    var intentsRule =
        object : IntentsTestRule<FolderActivity>(FolderActivity::class.java) {

            override fun getActivityIntent(): Intent {
                val intent = Intent(
                    ApplicationProvider.getApplicationContext(),
                    FolderActivity::class.java
                )
                intent.putExtra(
                    FolderActivity.FOLDER,
                    Folder("Inbox", 5, false)
                )
                return intent
            }

            override fun beforeActivityLaunched() {
                super.beforeActivityLaunched()
                declareMock<FolderViewModel> {
                    viewmodel = this
                    given(messages).willReturn { MutableLiveData(listOf(message)) }
                }
            }
        }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `messages are displayed`() {
        verify(viewmodel, times(1)).selectFolder(any())
        verify(viewmodel, times(1)).messages
        onView(withId(R.id.MessageRecyclerView)).check(hasItemCount(1))
    }

    @Test
    fun `correct message is opened on click`() {
        onView(withId(R.id.MessageRecyclerView)).check(hasItemCount(1))
        onView(withId(R.id.MessageRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<Adapter<Message>.ViewHolder>(
                    0,
                    click()
                )
            )

        intended(hasComponent(MessageActivity::class.java.canonicalName))
    }
}