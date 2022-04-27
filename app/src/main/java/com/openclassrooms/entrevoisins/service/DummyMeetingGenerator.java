package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> dummyMeetings = Arrays.asList(
            new Meeting(1, "Réunion A", "#bff40c", "28-04-2022, 14h30",
                    "Peach",  "mario@test.com; luigi@test.com", "Cette réunion portera sur..." , true),
            new Meeting(2, "Réunion B", "#0cf4cd", "28-04-2022, 16h30",
                    "Mario",  "peach@test.com; marotte@test.com", "Cette réunion portera sur..." ,true),
            new Meeting(3, "Réunion C", "#256e95", "29-04-2022, 8h30",
                    "Luigi",  "gaby@test.com; nana@test.com", "Cette réunion portera sur..." ,true),
            new Meeting(4, "Réunion D", "#259583", "29-04-2022, 8h30",
                    "Toad",  "mario@test.com; momo@test.com", "Cette réunion portera sur..." ,true),
            new Meeting(5, "Réunion E", "#3b1680", "30-04-2022, 10h30",
                    "Bowser",  "toad@test.com; maxou@test.com", "Cette réunion portera sur..." ,true),
            new Meeting(6, "Réunion F", "#d56dec", "30-04-2022, 10h30",
                    "Wario",  "bowser@test.com; koopa@test.com", "Cette réunion portera sur..." ,true),
            new Meeting(7, "Réunion G", "#cf1142", "02-05-2022, 14h30",
                    "Yoshi",  "mario@test.com; peach@test.com", "Cette réunion portera sur..." ,true),
            new Meeting(8, "Réunion H", "#f56f4b", "02-05-2022, 14h30",
                    "Toadette",  "wario@test.com; yoshi@test.com", "Cette réunion portera sur..." ,true),
            new Meeting(9, "Réunion I", "#f5f54b", "03-05-2022, 14h30",
                    "Donkey K.",  "koopa@test.com; wario@test.com", "Cette réunion portera sur..." ,true),
            new Meeting(10, "Réunion J", "#e425cd", "03-05-2022, 16h30",
                    "Koopa",  "ghighi@test.com; mario@test.com", "Cette réunion portera sur..." ,true)
    );

    static List<Meeting> generateNeighbours() {
        return new ArrayList<>(dummyMeetings);
    }
}
