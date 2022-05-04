package com.openclassrooms.mareu.service;

import com.openclassrooms.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> dummyMeetings = Arrays.asList(
            new Meeting(1, "Réunion A", "#bff40c", "28-04-2022, 14h30",
                    "Peach",  "mario@test.com; luigi@test.com", "Cette réunion portera sur..."),
            new Meeting(2, "Réunion B", "#0cf4cd", "28-04-2022, 16h30",
                    "Mario",  "peach@test.com; marotte@test.com", "Cette réunion portera sur..."),
            new Meeting(3, "Réunion C", "#256e95", "29-04-2022, 8h30",
                    "Luigi",  "gaby@test.com; nana@test.com", "Cette réunion portera sur..."),
            new Meeting(4, "Réunion D", "#259583", "29-04-2022, 8h30",
                    "Toad",  "mario@test.com; momo@test.com", "Cette réunion portera sur..."),
            new Meeting(5, "Réunion E", "#3b1680", "30-04-2022, 10h30",
                    "Bowser",  "toad@test.com; maxou@test.com", "Cette réunion portera sur..."),
            new Meeting(6, "Réunion F", "#d56dec", "30-04-2022, 10h30",
                    "Koopa",  "bowser@test.com; ghighi@test.com", "Cette réunion portera sur..."),
            new Meeting(7, "Réunion G", "#cf1142", "02-05-2022, 14h30",
                    "Yoshi",  "mario@test.com; peach@test.com", "Cette réunion portera sur...")
    );

    static List<Meeting> generateNeighbours() {
        return new ArrayList<>(dummyMeetings);
    }
}
