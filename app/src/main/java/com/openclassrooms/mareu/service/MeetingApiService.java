package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;

import java.util.Date;
import java.util.List;


/**
 * Neighbour API client
 */
public interface MeetingApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Deletes a neighbour
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a meeting
     * @param meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * Get a specific meeting data
     * @param id
     */
    Meeting getMeetingData(long id);

    /**
     * Get all Free Meetings Rooms by Date
     * @return {@link List}
     * @param date
     */
    List<Meeting> getMeetingsFilteredListByDate(Date date);

    /**
     * Get all Free Meetings Dates by RoomName
     * @return {@link List}
     */
    List<Meeting> getMeetingsListFilteredByRoomName(String roomName);

    /**
     * Set / unset neighbour in / from favorites list
     * @param meeting
     */
    void toggleFree(Meeting meeting);

    /**
     * Check if room is free according to roomName & meeting date (exception added to update meeting)
     * @param date
     * @param roomName
     * @param exception
     * @return {@link boolean}
     */
    boolean checkIfRoomIsFree(Date date, String roomName, Long exception);

}
