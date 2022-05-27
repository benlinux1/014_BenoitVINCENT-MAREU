
package com.openclassrooms.mareu.meeting_list;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.ui.meeting_list.ListMeetingActivity;
import com.openclassrooms.mareu.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class MeetingsListTest {

    // This is fixed
    private static int ITEMS_COUNT = 7;

    private ListMeetingActivity mActivity;

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule =
            new ActivityTestRule(ListMeetingActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myMeetingsList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_meetings))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingsList_deleteMeetingAction_shouldRemoveItem() {
        // Check meetings list count (actual is 7)
        onView(ViewMatchers.withId(R.id.list_meetings)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : check if item was deleted in the list (new is 6)
        onView(ViewMatchers.withId(R.id.list_meetings)).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * When we click on a meeting in the list, the meeting's detailed page is opened
     */
    @Test
    public void myMeetingsList_clickOnClickMeetingAction_shouldOpenMeetingDetails() {
        // Click on the third item in the meetings list
        onView(withId(R.id.list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        // Check if meeting details page is displayed
        onView(withId(R.id.meeting_page)).check(matches(isDisplayed()));
    }

    /**
     * When we click on a meeting in the list, the meeting's data is displayed in the right fields
     */
    @Test
    public void myMeetingsList_clickOnMeeting_shouldDisplayMeetingDataInDetailView() {
        // Click on the fifth item in the meetings list
        onView(withId(R.id.list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        // Check if meeting data is displayed on the details page
        Meeting meetingPosition = DI.getMeetingApiService().getMeetings().get(4);
        onView(withId(R.id.meeting_detail_location_room)).check(matches(withText("Salle \"" + meetingPosition.getRoomName() +"\"")));
        onView(withId(R.id.meeting_detail_second_title)).check(matches(withText(meetingPosition.getSubject())));
        onView(withId(R.id.meeting_detail_description)).check(matches(withText(meetingPosition.getDescription())));
    }


    /**
     * When we click on ADD BUTTON, the new meeting form is displayed
     */
    @Test
    public void myMeetingsList_addMeetingAction_shouldOpenAddMeetingForm() {
        // Check meetings list count (actual is 7)
        onView(ViewMatchers.withId(R.id.list_meetings)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on the add button
        onView(withId(R.id.add_meeting)).perform(click());
        // Check if meeting form is displayed
        onView(withId(R.id.meeting_fields_page)).check(matches(isDisplayed()));
    }

    /**
     * When we click on EDIT BUTTON in meeting details page, the edit meeting form is displayed
     */
    @Test
    public void myMeetingsList_editMeetingAction_shouldOpenEditMeetingForm() {
        // Check meetings list count (actual is 7)
        onView(ViewMatchers.withId(R.id.meeting_page));
        // When perform a click on the add button
        onView(withId(R.id.meeting_detail_update_button)).perform(click());

        // Check if meeting form is displayed
        onView(withId(R.id.meeting_fields_page)).check(matches(isDisplayed()));
    }

}