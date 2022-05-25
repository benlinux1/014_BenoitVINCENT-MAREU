
package com.openclassrooms.mareu.meeting_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_meetings))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingsList_deleteMeetingAction_shouldRemoveItem() {
        // Check neighbours list count (actual is 7)
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
        // Click on the third item in the neighbour list
        onView(withId(R.id.list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        // Check if neighbour's profile is displayed
        onView(withId(R.id.meeting_page)).check(matches(isDisplayed()));
    }

    /**
     * When we click on a meeting in the list, the meeting's data is displayed in the right fields
     */
    @Test
    public void myMeetingList_clickOnMeeting_shouldDisplayMeetingDataInDetailView() {
        // Click on the fifth item in the neighbour list
        onView(withId(R.id.list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        // Check if meeting data is displayed on his profile's page
        Meeting meetingPosition = DI.getMeetingApiService().getMeetings().get(4);
        onView(withId(R.id.meeting_detail_location_room)).check(matches(withText("Salle \"" + meetingPosition.getRoomName() +"\"")));
        onView(withId(R.id.meeting_detail_second_title)).check(matches(withText(meetingPosition.getSubject())));
        onView(withId(R.id.meeting_detail_description)).check(matches(withText(meetingPosition.getDescription())));
    }
}