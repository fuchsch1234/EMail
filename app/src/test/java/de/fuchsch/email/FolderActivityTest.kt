package de.fuchsch.email

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.nhaarman.mockitokotlin2.*
import de.fuchsch.email.activity.FolderActivity
import de.fuchsch.email.model.Folder
import de.fuchsch.email.model.Message
import de.fuchsch.email.repository.MailRepository
import de.fuchsch.email.util.RecyclerViewItemCountAssertion.Companion.hasItemCount
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.mock.declareMock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class FolderActivityTest : KoinTest {

    private lateinit var repository: MailRepository

    private val intent =
        Intent(ApplicationProvider.getApplicationContext(), FolderActivity::class.java).apply {
            putExtra(
                FolderActivity.FOLDER,
                Folder("Inbox", 5, false)
            )
        }

    @Before
    fun setUp() {
        declareMock<MailRepository> {
            repository = this
            stub {
                onBlocking { getMessages(any()) } doReturn listOf(
                    Message("Test Subject", "Test Message", "", emptyList(), 1)
                )
            }
        }
    }

    @Test
    fun `messages from repository are displayed`() {
        ActivityScenario.launch<FolderActivity>(intent)

        onView(withId(R.id.MessageRecyclerView)).check(hasItemCount(1))

    }

}