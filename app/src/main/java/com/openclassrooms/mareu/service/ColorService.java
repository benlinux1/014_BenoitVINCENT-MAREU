package com.openclassrooms.mareu.service;

import java.util.Random;

public class ColorService {

    /**
     * Generate a random color
     * @return String
     */
    public static String randomColor() {
        // create object of Random class
        Random obj = new Random();
        int rand_num = obj.nextInt(0xffffff + 1);
        // format it as hexadecimal string and print
        String colorCode = String.format("#%06x", rand_num);
        return colorCode;
    }
}
