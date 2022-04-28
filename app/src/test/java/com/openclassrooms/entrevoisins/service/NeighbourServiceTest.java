package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Meeting;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

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
        Meeting createdMeeting = new Meeting( (long) service.getMeetings().size(), "Z", "#FF80AB", "01/01/2023", "14:00", "Harry Potter", "abc@testmail.com", true );
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
        service.toggleFree(roomToSetOccupied);
        // Check if meeting is NOT in the free lists and stay in global list
        assertFalse(service.getFreeMeetingsListByDate().contains(roomToSetOccupied));
        assertFalse(service.getFreeMeetingsListByName().contains(roomToSetOccupied));
        assertTrue(service.getMeetings().contains(roomToSetOccupied));
    }

    @Test
    public void deleteOccupiedMeetingAttributeWithSuccess() {
        // Take the first free meeting room in global list
        Meeting meetingToDeleteFromOccupiedMeetings = service.getMeetings().get( 0 );
        // Call method to set meeting room occupied
        service.toggleFree(meetingToDeleteFromOccupiedMeetings);
        // Call reverse method to set meeting room free
        service.toggleFree(meetingToDeleteFromOccupiedMeetings);
        // Check if meeting is in the free lists and is still in global list
        assertTrue(service.getFreeMeetingsListByDate().contains(meetingToDeleteFromOccupiedMeetings));
        assertTrue(service.getFreeMeetingsListByName().contains(meetingToDeleteFromOccupiedMeetings));
        assertTrue(service.getMeetings().contains(meetingToDeleteFromOccupiedMeetings));
    }

    @Test
    public void deleteFreeMeetingFromApiWithSuccess() {
        // Take the first meeting in global list
        Meeting meetingToDeleteFromApi = service.getMeetings().get( 0 );
        // Call method to set meeting room occupied
        service.toggleFree(meetingToDeleteFromApi);
        // Call reverse method to delete room meeting from occupied rooms
        service.toggleFree(meetingToDeleteFromApi);
        // Check if meeting is removed from the free list AND from global list
        assertFalse(service.getFreeMeetingsListByName().contains(meetingToDeleteFromApi));
        assertFalse(service.getFreeMeetingsListByDate().contains(meetingToDeleteFromApi));
        assertFalse(service.getMeetings().contains(meetingToDeleteFromApi));
    }
}