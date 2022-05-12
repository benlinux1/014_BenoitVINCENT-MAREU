package com.openclassrooms.mareu.events;

import com.openclassrooms.mareu.model.Participant;

/**
 * Event fired when a user deletes a meeting Participant
 */
public class DeleteParticipantEvent {

    /**
     * Participant to delete
     */
    public Participant participant;

    /**
     * Constructor.
     * @param participant
     */
    public DeleteParticipantEvent(Participant participant) {
        this.participant = participant;
    }

}