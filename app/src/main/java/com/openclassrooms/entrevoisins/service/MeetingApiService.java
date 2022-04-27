package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Meeting;

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
     */
    List<Meeting> getFreeMeetingsListByDate();

    /**
     * Get all Free Meetings Dates by RoomName
     * @return {@link List}
     */
    List<Meeting> getFreeMeetingsListByName();

    /**
     * Set / unset neighbour in / from favorites list
     * @param meeting
     */
    void toggleFree(Meeting meeting);

}
