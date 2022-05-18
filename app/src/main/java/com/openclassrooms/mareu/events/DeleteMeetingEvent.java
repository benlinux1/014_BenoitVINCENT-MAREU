package com.openclassrooms.mareu.events;

import com.openclassrooms.mareu.model.Meeting;

/**
 * Event fired when a user deletes a Neighbour
 */
public class DeleteMeetingEvent {

    /**
     * Meeting to delete
     */
    public Meeting meeting;

    /**
     * Constructor.
     */
    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }

}
