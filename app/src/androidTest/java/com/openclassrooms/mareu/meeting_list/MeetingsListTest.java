
package com.openclassrooms.mareu.meeting_list;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.ui.meeting_list.ListMeetingActivity;
import com.openclassrooms.mareu.utils.DeleteViewAction;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class MeetingsListTest {


    private final int ITEMS_COUNT = 7;

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
        // Click on the fifth item in the meetings list
        onView(withId(R.id.list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        // Check if meeting details page is displayed
        onView(withId(R.id.meeting_page)).check(matches(isDisplayed()));
        // When perform a click on the update button
        onView(withId(R.id.meeting_detail_update_button)).perform(click());
        // Check if alert dialog is displayed
        onView(withText("Voulez vous vraiment modifier cette réunion ?")).check(matches(isDisplayed()));
        // When we click "OK" confirmation button
        onView(withId(android.R.id.button1)).perform(click());
        // Check if update meeting form is displayed
        onView(withId(R.id.meeting_fields_page)).check(matches(isDisplayed()));
        // Check if meeting data (here subject) is displayed in the right field
        Meeting meetingPosition = DI.getMeetingApiService().getMeetings().get(4);
        onView(withId(R.id.add_subject_input)).check(matches(withText(meetingPosition.getSubject())));
    }

    /**
     * When we add a meeting, the item is in the meetings list
     * DONT' RUN this test alone (it fails because it includes modified item count, given item deleted before)
     */
    @Test
    public void myMeetingsList_addingNewMeetingAction_shouldAddItem() {
        // Check meetings list count (6, because of deleting test before)
        onView(withId(R.id.list_meetings)).check(withItemCount(ITEMS_COUNT-1));
        // Click on the creation button to add a new meeting
        onView(withId(R.id.add_meeting))
                .perform(click());
        // Click on the date field
        onView(withId(R.id.add_date_input))
                .perform(click());
        // Set meeting date (day / mounth / year)
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022,  5, 19));
        // Click OK button
        onView(withId(android.R.id.button1)).perform(click());
        // Set meeting hour (hour / minute)
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(14,  0));
        // Click OK button
        onView(withId(android.R.id.button1)).perform(click());
        // Click on the subject field
        onView(withId(R.id.add_subject_input))
                .perform(click());
        // Enter meeting's subject
        onView(withId(R.id.add_subject_input))
                .perform(typeText("New Meeting"), closeSoftKeyboard());
        // Click on 2nd meeting room radio button ("Mario")
        onView(withId(R.id.radioButton_room2))
                .perform(click());
        // Click on the description field
        onView(withId(R.id.add_description))
                .perform(click());
        // Enter meeting's description
        onView(withId(R.id.add_description))
                .perform(typeText("This is my meeting description"));
        // Click on the participants field
        onView(withId(R.id.add_participants_input))
                .perform(click());
        // Enter a participant email (checked with email regex)
        onView(withId(R.id.add_participants_input))
                .perform(typeText("participant@test.com"), pressImeActionButton());
        // Click on the creation button to add this new meeting
        onView(withId(R.id.create))
                .perform(scrollTo(), click());
        // Then : check if item was added to the list ()
        onView(withId(R.id.list_meetings)).check(withItemCount(ITEMS_COUNT));
    }

    /**
     * When we use filtering by date option in menu, the meeting list contains meeting with the right date
     */
    @Test
    public void myMeetingsList_filteringByDateMeetingAction_shouldDisplayItemsWithRightDate() {
        // Check meetings list count
        onView(withId(R.id.list_meetings)).check(withItemCount(ITEMS_COUNT));
        // When we click on menu option
        try {
            onView(withId(R.id.filter_date)).perform(click());
        } catch (NoMatchingViewException e) {
            openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
            onView(withText(R.string.menu_filter_date)).perform(click());
        }
        // Set filtering date (day / month / year)
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022,  5, 19));
        // Click OK button
        onView(withId(android.R.id.button1)).perform(click());
        // Check if meeting details page is displayed
        onView(withId(R.id.list_meetings)).check(matches(isDisplayed()));
        // Click on the first meeting in the list
        onView(withId(R.id.list_meetings)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Check if meeting details page is displayed
        onView(withId(R.id.meeting_page)).check(matches(isDisplayed()));
        // Check if meeting date matches with set date in filter by date option
        onView(withId(R.id.meeting_detail_date)).toString().contains("Le 19/05/2022");
    }

    /**
     * When we use filtering by room name option in menu, the meeting list contains meeting with the right rooms
     */
    @Test
    public void myMeetingsList_filteringByRoomNameMeetingAction_shouldDisplayMeetingsWithRightRoomName() {
        // Check meetings list count
        onView(withId(R.id.list_meetings)).check(withItemCount(ITEMS_COUNT));
        // When we click on menu option
        try {
            onView(withId(R.id.filter_room)).perform(click());
        } catch (NoMatchingViewException e) {
            openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
            onView(withText(R.string.menu_filter_room)).perform(click());
        }
        String room = "Mario";
        onView(withText(room)).perform(click());
        // Click OK button
        onView(withId(android.R.id.button1)).perform(click());
        // Check if meeting details page is displayed
        onView(withId(R.id.list_meetings)).check(matches(isDisplayed()));
        // Click on the first meeting in the list
        onView(withId(R.id.list_meetings)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Check if meeting details page is displayed
        onView(withId(R.id.meeting_page)).check(matches(isDisplayed()));
        // Check if meeting room matches with set room in filter by room name option
        onView(withId(R.id.meeting_detail_location_room)).check(matches(withText("Salle \"Mario\"")));
    }

    /**
     * When we add a meeting, the item is in the meetings list
     * DONT' RUN this test alone (fails because includes item count with item deleted before)
     */
    @Test
    public void myMeetingsDetails_editingMeetingAction_shouldUpdateItem() {
        // Click on the fifth item in the meetings list
        onView(withId(R.id.list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        // Check if meeting details page is displayed
        onView(withId(R.id.meeting_page)).check(matches(isDisplayed()));
        // When perform a click on the update button
        onView(withId(R.id.meeting_detail_update_button)).perform(click());
        // Check if alert dialog is displayed
        onView(withText("Voulez vous vraiment modifier cette réunion ?")).check(matches(isDisplayed()));
        // When we click "OK" confirmation button
        onView(withId(android.R.id.button1)).perform(click());
        // Check if update meeting form is displayed
        onView(withId(R.id.meeting_fields_page)).check(matches(isDisplayed()));
        // Check if meeting data (here subject) is displayed in the right field
        Meeting meetingPosition = DI.getMeetingApiService().getMeetings().get(4);
        onView(withId(R.id.add_subject_input)).check(matches(withText(meetingPosition.getSubject())));
        // Click on the date field
        onView(withId(R.id.add_date_input))
                .perform(click());
        // Set meeting date (day / month / year)
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2022,  5, 19));
        // Click OK button
        onView(withId(android.R.id.button1)).perform(click());
        // Set meeting hour (hour / minute)
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(14,  0));
        // Click OK button
        onView(withId(android.R.id.button1)).perform(click());
        // Click on the update button to update meeting with new date set
        onView(withId(R.id.create))
                .perform(scrollTo(), click());
        // Check if meeting details page is displayed
        onView(withId(R.id.meeting_page)).check(matches(isDisplayed()));
        // Check if meeting data (here new date) is displayed and formatted in the right field
        onView(withId(R.id.meeting_detail_date)).check(matches(withText("Le 19/05/2022 à 14:00")));
    }
}