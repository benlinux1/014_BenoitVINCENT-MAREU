package com.openclassrooms.mareu.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.openclassrooms.mareu.ui.meeting_list.AddMeetingActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeService {

    private static Calendar date;

    /**
     * Launch calendar instance with current date, and send formatted date to date input
     */
    public static void setDate(EditText dateInput, Context context) {
        final Calendar currentDate = Calendar.getInstance(Locale.FRANCE);
        date = Calendar.getInstance(Locale.FRANCE);

        // Date Select Listener
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        // Format & set date + time in meeting date field
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
                        dateInput.setText(dateFormat.format(date.getTime()));
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    /**
     * Get date from string with parsing
     * @param dateString
     * @return Date
     */
    public static Date getDate(String dateString) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
        Date date = dateFormat.parse(dateString);
        Calendar cal = Calendar.getInstance();
        assert date != null;
        cal.setTime(date);
        return date;
    }
}
