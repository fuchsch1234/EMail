package de.fuchsch.email.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers
import junit.framework.AssertionFailedError
import org.hamcrest.Matcher
import org.hamcrest.Matchers

class RecyclerViewItemCountAssertion(private val matcher: Matcher<Int>): ViewAssertion {

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        noViewFoundException?.also { throw it }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter ?: throw AssertionFailedError("Recyclerview has no adapter")
        ViewMatchers.assertThat("RecyclerView item count mismatch", adapter.itemCount, matcher)
    }

    companion object {

        fun hasItemCount(count: Int) = RecyclerViewItemCountAssertion(Matchers.`is`(count))

    }

}
