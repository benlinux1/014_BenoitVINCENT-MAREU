package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.Participant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> dummyMeetings = Arrays.asList(
            new Meeting(1, "Réunion A", "#bff40c", new Date(1652882400000L),
                    "Peach", "mario@test.com; luigi@test.com", "Cette réunion portera sur..."),
            new Meeting(2, "Réunion B", "#0cf4cd", new Date(1652882400000L),
                    "Mario",  "peach@test.com; marotte@test.com", "Cette réunion portera sur..."),
            new Meeting(3, "Réunion C", "#256e95", new Date(1652950800000L),
                    "Luigi",  "gaby@test.com; nana@test.com;", "Cette réunion portera sur..."),
            new Meeting(4, "Réunion D", "#259583", new Date(1652950800000L),
                    "Toad",  "mario@test.com; momo@test.com;", "Cette réunion portera sur..."),
            new Meeting(5, "Réunion E", "#3b1680", new Date(1653040800000L),
                    "Bowser",  "toad@test.com; maxou@test.com;", "Cette réunion portera sur..."),
            new Meeting(6, "Réunion F", "#d56dec", new Date(1653055200000L),
                    "Koopa",  "bowser@test.com; ghighi@test.com;", "Cette réunion portera sur..."),
            new Meeting(7, "Réunion G", "#cf1142", new Date(1653130800000L),
                    "Yoshi",  "mario@test.com; peach@test.com;", "Cette réunion portera sur...")
    );

    static List<Meeting> generateNeighbours() {
        return new ArrayList<>(dummyMeetings);
    }
}
