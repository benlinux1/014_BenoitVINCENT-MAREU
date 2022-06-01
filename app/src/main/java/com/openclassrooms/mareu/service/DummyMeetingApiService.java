package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyMeetingApiService implements MeetingApiService {

    private final List<Meeting> meetings = DummyMeetingGenerator.generateNeighbours();

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
     * Get the meeting list according to selected Room Filter
     * @param roomName
     */
    @Override
    public List<Meeting> getMeetingsListFilteredByRoomName(String roomName) {
        List<Meeting> filteredMeetingRooms = new ArrayList<>();
        for (int i = 0; i < meetings.size(); i++)
            if (meetings.get(i).getRoomName().equals(roomName)) {
                filteredMeetingRooms.add(meetings.get(i));
            }
        return filteredMeetingRooms;
    }

    /**
     * Check if room is free according to roomName & meeting date (meeting max duration 1 hour & 59 minutes)
     * @param date
     * @param roomName
     * @param exceptionId
     * @return boolean
     */
    public boolean checkIfRoomIsFree(Date date, String roomName, Long exceptionId) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(date);
        // loop in meetings
        for (int i = 0; i < meetings.size(); i++) {
            // Get meeting date with Calendar
            Calendar meetingDate = Calendar.getInstance();
            meetingDate.setTime(meetings.get(i).getDate());
            // Set meeting max duration (1 hour & 59 minutes)
            int maxDurationHour = meetingDate.get(Calendar.HOUR_OF_DAY)+1;
            int maxDurationMinute = meetingDate.get(Calendar.MINUTE)+59;
            int minDurationHour = meetingDate.get(Calendar.HOUR_OF_DAY)-1;
            int minDurationMinute = meetingDate.get(Calendar.MINUTE)-59;
            // Set same time conditions to compare selected dateTime and all meetings dateTime
            boolean sameTime = selectedDate.get(Calendar.DAY_OF_YEAR) == meetingDate.get(Calendar.DAY_OF_YEAR) &&
                selectedDate.get(Calendar.YEAR) == meetingDate.get(Calendar.YEAR) &&
                (selectedDate.get(Calendar.HOUR_OF_DAY) >= meetingDate.get(Calendar.HOUR_OF_DAY)
                        && (selectedDate.get(Calendar.HOUR_OF_DAY) <= maxDurationHour && selectedDate.get(Calendar.MINUTE) <= maxDurationMinute))
                        || (selectedDate.get(Calendar.HOUR_OF_DAY) <= meetingDate.get(Calendar.HOUR_OF_DAY)
                    && (selectedDate.get(Calendar.HOUR_OF_DAY) >= minDurationHour && selectedDate.get(Calendar.MINUTE) >= minDurationMinute));
            boolean sameRoom = meetings.get(i).getRoomName().equals(roomName);
            boolean differentId = meetings.get(i).getId() != exceptionId;
            if (sameRoom && sameTime && differentId) {
                return false;
            }
        }
        return true;
    }
}
