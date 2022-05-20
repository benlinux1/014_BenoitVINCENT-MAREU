package com.openclassrooms.mareu.ui.meeting_list;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.MeetingApiService;
import com.openclassrooms.mareu.service.ValidationService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListMeetingActivity extends AppCompatActivity {

    // UI Components
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.list_meetings)
    RecyclerView mRecyclerView;

    private RadioGroup mFirstGroup;
    private RadioGroup mSecondGroup;
    private RadioButton mMeetingRoom1;
    private RadioButton mMeetingRoom2;
    private RadioButton mMeetingRoom3;
    private RadioButton mMeetingRoom4;
    private RadioButton mMeetingRoom5;
    private RadioButton mMeetingRoom6;
    private RadioButton mMeetingRoom7;
    private RadioButton mMeetingRoom8;
    private RadioButton mMeetingRoom9;
    private RadioButton mMeetingRoom10;
    private boolean isChecking = true;
    private int mCheckedId = R.id.radioButton_room1;

    private List<Meeting> mMeetingsList = new ArrayList<>();
    private MeetingApiService mApiService = DI.getMeetingApiService();
    MyMeetingRecyclerViewAdapter adapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.filter_date:
                dateDialog();
                return true;
            case R.id.filter_room:
                roomDialog();
                return true;
            case R.id.filter_reset:
                resetFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resetFilter() {
        mMeetingsList.clear();
        mMeetingsList.addAll(mApiService.getMeetings());
        adapter = new MyMeetingRecyclerViewAdapter(mMeetingsList);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void dateDialog() {
        int selectedYear = 2022;
        int selectedMonth = 04;
        int selectedDayOfMonth = 18;

        // Date Select Listener
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar cal = Calendar.getInstance();
                cal.set(i, i1, i2);
                mMeetingsList.clear();
                mMeetingsList = mApiService.getMeetingsFilteredListByDate(cal.getTime());
                adapter = new MyMeetingRecyclerViewAdapter(mMeetingsList);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        };

        // Create DatePickerDialog (Spinner Mode):
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);

        // Show
        datePickerDialog.show();
    }

    private void roomDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder( ListMeetingActivity.this );
        final View dialogView = getLayoutInflater().inflate(R.layout.room_filter_dialog, null);
        dialog.setView(dialogView);
        dialog.show();
        mFirstGroup = dialogView.findViewById(R.id.radioGroup_1_to_5);
        mSecondGroup = dialogView.findViewById(R.id.radioGroup_6_to_10);
        mMeetingRoom1 = dialogView.findViewById(R.id.radioButton_room1);
        mMeetingRoom2 = dialogView.findViewById(R.id.radioButton_room2);
        mMeetingRoom3 = dialogView.findViewById(R.id.radioButton_room3);
        mMeetingRoom4 = dialogView.findViewById(R.id.radioButton_room4);
        mMeetingRoom5 = dialogView.findViewById(R.id.radioButton_room5);
        mMeetingRoom6 = dialogView.findViewById(R.id.radioButton_room6);
        mMeetingRoom7 = dialogView.findViewById(R.id.radioButton_room7);
        mMeetingRoom8 = dialogView.findViewById(R.id.radioButton_room8);
        mMeetingRoom9 = dialogView.findViewById(R.id.radioButton_room9);
        mMeetingRoom10 = dialogView.findViewById(R.id.radioButton_room10);
        ValidationService.checkIfRoomIsChecked(mFirstGroup, mSecondGroup);


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mMeetingsList.addAll(mApiService.getMeetings());
        adapter = new MyMeetingRecyclerViewAdapter(mMeetingsList);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();





    }

    @OnClick(R.id.add_meeting)
    void addMeeting() {
        AddMeetingActivity.navigate(this);
    }
}
