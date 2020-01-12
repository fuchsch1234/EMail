package de.fuchsch.email

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.nhaarman.mockitokotlin2.*
import de.fuchsch.email.activity.AccountActivity
import de.fuchsch.email.activity.MainActivity
import de.fuchsch.email.database.entity.Account
import de.fuchsch.email.database.entity.AccountSetting
import de.fuchsch.email.database.entity.IMAPProtocol
import de.fuchsch.email.model.Folder
import de.fuchsch.email.util.RecyclerViewItemCountAssertion.Companion.hasItemCount
import de.fuchsch.email.viewmodel.AccountViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.mock.declareMock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.LooperMode


@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class AccountActivityTest : KoinTest {

    private lateinit var viewModel: AccountViewModel

    @Before
    fun setup() {
        declareMock<AccountViewModel> {
            given(folders).willReturn {
                MutableLiveData(listOf(Folder("Inbox", "INBOX", 1, true)))
            }
            viewModel = this
        }
    }

    @Test
    fun `first test`() {
        val intent =
            Intent(ApplicationProvider.getApplicationContext(), AccountActivity::class.java)
        intent.putExtra(
            MainActivity.ACCOUNT,
            Account("f", AccountSetting("", "", "", IMAPProtocol.IMAPS))
        )

        ActivityScenario.launch<AccountActivity>(intent)

        verify(viewModel, times(1)).select(any())
        verify(viewModel, times(1)).folders
        onView(withId(R.id.FolderRecyclerView)).check(hasItemCount(1))
    }
}