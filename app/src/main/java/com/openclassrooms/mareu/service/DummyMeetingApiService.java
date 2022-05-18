package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> meetings = DummyMeetingGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    /**
     * {@inheritDoc}
     * @param meeting
     */
    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    /**
     * loop on each neighbour in List
     * return neighbour's data with @param id
     */
    @Override
    public Meeting getMeetingData(long id) {
        Meeting meeting = null;
        for(Meeting i : meetings) {
            if(i.getId() == id){
                meeting = i;
                break;
            }
        }
        return meeting;
    }

    /**
     * Get the free meeting rooms list by date
     * @param time
     */
    @Override
    public List<Meeting> getFreeMeetingsListByDate(Date time) {
        List<Meeting> freeMeetingRooms = new ArrayList<>();
        for (Meeting meeting : getMeetings())
            if (meeting.isFree()) {
                freeMeetingRooms.add(meeting);
            }
        return freeMeetingRooms;
    }

    /**
     * Get the free meeting rooms list by name
     */
    @Override
    public List<Meeting> getFreeMeetingsListByName() {
        List<Meeting> freeMeetingRooms = new ArrayList<>();
        for (Meeting meeting : getMeetings())
            if (meeting.isFree()) {
                freeMeetingRooms.add(meeting);
            }
        return freeMeetingRooms;
    }

    /**
     * Set / unset neighbour in / from favorites list
     * {@param neighbour}
     */
    @Override
    public void toggleFree(Meeting meeting) {
        if (meeting.isFree()) {
            meeting.setFree(false);
        } else {
            meeting.setFree(true);
        }
    }

    @Override
    public void updateMeeting(Meeting meetingToUpdate, int index) {
        meetings.set(index, meetingToUpdate);
    }

}
