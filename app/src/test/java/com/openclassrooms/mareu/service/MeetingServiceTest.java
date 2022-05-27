package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Meeting service
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    /**
    * Unit test that checks we get meetings global list
     */
    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.dummyMeetings;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    /**
     * Unit test that checks a given meeting is deleted with success
     */
    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    /**
     * Unit test that checks a new meeting can be created with success
     */
    @Test
    public void addMeetingWithSuccess() {
        // New meeting data
        Meeting createdMeeting = new Meeting( (long) service.getMeetings().size(), "Fake subject", "#FF80AB", new Date(1652882400000L), "Secret Room", "Harry Potter", "abc@testmail.com" );
        // Call method to create meeting in API Service
        service.createMeeting(createdMeeting);
        // Check if created meeting is in the list
        assertTrue(service.getMeetings().contains(createdMeeting));
    }

    /**
     * Unit test that checks a given meeting can be updated with success
     */
    @Test
    public void editMeetingWithSuccess() {
        // New meeting data
        Meeting meetingToUpdate = service.getMeetings().get(0);
        meetingToUpdate.setDescription("Here is the new description of updated meeting");
        // Check if created meeting is in the list
        assertTrue(service.getMeetings().get(0).getDescription().contains("Here is the new description of updated meeting"));
    }

    /**
     * Unit test that checks the filtering service by date (@param date) return a meeting list that contains the created meeting (with the same date sent in parameter)
     */
    @Test
    public void getMeetingFilteredByDateWithSuccess() {
        // New meeting data
        Meeting createdMeeting = new Meeting((long) service.getMeetings().size(), "Fake subject", "#FF80AB", new Date(1652882400000L), "Secret Room", "Harry Potter", "abc@testmail.com");
        // Call method to create meeting in API Service
        service.createMeeting(createdMeeting);
        // Call method to get meetings list filtered by date & check if created meeting is in the list filtered by date
        assertTrue(service.getMeetingsFilteredListByDate(createdMeeting.getDate()).contains(createdMeeting));
    }

    /**
     * Unit test that checks the filtering service by room name (@param string roomName) return a meeting list that contains the created meeting (with the same room name sent in parameter)
     */
    @Test
    public void getMeetingFilteredByRoomName() {
        // New meeting data
        Meeting createdMeeting = new Meeting((long) service.getMeetings().size(), "Fake subject", "#FF80AB", new Date(1652882400000L), "Secret Room", "Harry Potter", "abc@testmail.com");
        // Call method to create meeting in API Service
        service.createMeeting(createdMeeting);
        // Call method to get meetings list filtered by date & check if created meeting is in the list filtered by date
        assertTrue(service.getMeetingsListFilteredByRoomName(createdMeeting.getRoomName()).contains(createdMeeting));
    }
}
