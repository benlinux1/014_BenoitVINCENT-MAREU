package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
     * @param date
     * @return {@link List}
     */
    @Override
    public List<Meeting> getMeetingsFilteredListByDate(Date date) {
        List<Meeting> meetingListByDate = new ArrayList<>();
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(date);
        for (int i = 0; i < meetings.size(); i++) {
            Calendar meetingDate = Calendar.getInstance();
            meetingDate.setTime(meetings.get(i).getDate());
            boolean sameDay = selectedDate.get(Calendar.DAY_OF_YEAR) == meetingDate.get(Calendar.DAY_OF_YEAR) &&
                    selectedDate.get(Calendar.YEAR) == meetingDate.get(Calendar.YEAR);
            if (sameDay)
                meetingListByDate.add(meetings.get(i));
        }
        return meetingListByDate;
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
     * {@param meeting}
     */
    @Override
    public void toggleFree(Meeting meeting) {
        if (meeting.isFree()) {
            meeting.setFree(false);
        } else {
            meeting.setFree(true);
        }
    }
}
