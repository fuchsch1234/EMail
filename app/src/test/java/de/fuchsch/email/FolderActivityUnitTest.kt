package de.fuchsch.email

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.nhaarman.mockitokotlin2.*
import de.fuchsch.email.activity.FolderActivity
import de.fuchsch.email.model.Folder
import de.fuchsch.email.model.Message
import de.fuchsch.email.util.RecyclerViewItemCountAssertion.Companion.hasItemCount
import de.fuchsch.email.viewmodel.FolderViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.mock.declareMock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class FolderActivityUnitTest: KoinTest {

    lateinit var viewmodel: FolderViewModel

    @Before
    fun setUp() {
        declareMock<FolderViewModel> {
            viewmodel = this
            given(messages).willReturn {
                MutableLiveData(listOf(
                    Message("Test Subject", "Test Message", "", emptyList())
                ))
            }
        }
    }

    @Test
    fun `messages are displayed`() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), FolderActivity::class.java)
        intent.putExtra(
            FolderActivity.FOLDER,
            Folder("Inbox", 5, false)
        )
        ActivityScenario.launch<FolderActivity>(intent)

        verify(viewmodel, times(1)).selectFolder(any())
        verify(viewmodel, times(1)).messages
        onView(withId(R.id.MessageRecyclerView)).check(hasItemCount(1))
    }

}