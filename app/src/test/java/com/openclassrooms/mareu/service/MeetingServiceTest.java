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
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.dummyMeetings;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void addMeetingWithSuccess() {
        // New meeting data
        Meeting createdMeeting = new Meeting( (long) service.getMeetings().size(), "Fake subject", "#FF80AB", new Date(1652882400000L), "Secret Room", "Harry Potter", "abc@testmail.com" );
        // Call method to create meeting in API Service
        service.createMeeting(createdMeeting);
        // Check if created meeting is in the list
        assertTrue(service.getMeetings().contains(createdMeeting));
    }

    @Test
    public void setMeetingRoomOccupiedWithSuccess() {
        // Take the first meetingRoom in the global list
        Meeting roomToSetOccupied = service.getMeetings().get( 0 );
        // Call method to set meeting occupied
        // Check if meeting is NOT in the free lists and stay in global list
        assertTrue(service.getMeetings().contains(roomToSetOccupied));
    }

    @Test
    public void deleteOccupiedMeetingAttributeWithSuccess() {
        // Take the first free meeting room in global list
        Meeting meetingToDeleteFromOccupiedMeetings = service.getMeetings().get( 0 );
        // Call method to set meeting room occupied

        // Check if meeting is in the free lists and is still in global list
        assertTrue(service.getMeetings().contains(meetingToDeleteFromOccupiedMeetings));
    }

    @Test
    public void deleteFreeMeetingFromApiWithSuccess() {
        // Take the first meeting in global list
        Meeting meetingToDeleteFromApi = service.getMeetings().get( 0 );
        // Call method to set meeting room occupied

        // Check if meeting is removed from the free list AND from global list
        assertFalse(service.getMeetings().contains(meetingToDeleteFromApi));
    }
}
