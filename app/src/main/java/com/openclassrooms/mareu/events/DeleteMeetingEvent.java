package com.openclassrooms.mareu.events;

import com.openclassrooms.mareu.model.Meeting;

/**
 * Event fired when a user deletes a Neighbour
 */
public class DeleteMeetingEvent {

    /**
     * Neighbour to delete
     */
    public Meeting meeting;

    /**
     * Constructor.
     * @param meeting
     */
    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }

}
