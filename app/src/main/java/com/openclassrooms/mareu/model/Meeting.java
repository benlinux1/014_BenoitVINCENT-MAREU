package com.openclassrooms.mareu.model;

import java.util.Objects;

/**
 * Model object representing a Neighbour
 */
public class Meeting {

    /** Identifier */
    private long id;

    /** Meeting subject */
    private String subject;

    /** Avatar Color */
    private String avatarColor;

    /** Meeting Date */
    private String mDate;

    /** Room Name */
    private String roomName;

    /** Participants List */
    private String participants;

    /** Subject description */
    private String description;

    /** Free room */
    private Boolean isFree;

    /**
     * Constructor
     * @param id
     * @param subject
     * @param avatarColor
     * @param mDate
     * @param roomName
     * @param participants
     * @param description
     */

    public Meeting(long id, String subject, String avatarColor,
                   String mDate, String roomName, String participants, String description) {
        this.id = id;
        this.subject = subject;
        this.avatarColor = avatarColor;
        this.mDate = mDate;
        this.roomName = roomName;
        this.participants = participants;
        this.description = description;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAvatarColor() {
        return avatarColor;
    }

    public void setAvatarColor(String avatarColor) {
        this.avatarColor = avatarColor;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        this.isFree = free;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(id, meeting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
