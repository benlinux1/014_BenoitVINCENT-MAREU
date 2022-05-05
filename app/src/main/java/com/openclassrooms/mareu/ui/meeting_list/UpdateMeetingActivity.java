package com.openclassrooms.mareu.ui.meeting_list;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.MeetingApiService;
import com.openclassrooms.mareu.service.ValidationService;

import java.util.List;

import butterknife.BindView;

public class UpdateMeetingActivity extends AppCompatActivity {

    @BindView(R.id.add_subject_layout)
    TextInputLayout subjectLayout;

    @BindView(R.id.add_participants_layout)
    TextInputLayout participantsLayout;
    @BindView(R.id.add_participants_input)
    EditText participantInput;

    private ImageView mMeetingColor;
    private EditText mMeetingSubject;
    private EditText mMeetingDate;
    private TextView mMeetingRoom;
    private TextView mMeetingParticipants;
    private EditText mMeetingDescription;
    private MeetingApiService mApiService;

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
    private MaterialButton mUpdateButton;

    private List<String> participants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MeetingApiService mApiService = DI.getMeetingApiService();

        Intent getProfileIntent = getIntent();
        long id = getProfileIntent.getLongExtra("MEETING_ID",-1);
        Meeting meeting = mApiService.getMeetingData(id);

        getViews();
        setMeetingInfo(meeting);
        setMeetingRoomChecked(meeting);
        ValidationService.checkIfRoomIsChecked(findViewById(R.id.radioGroup_1_to_5), findViewById(R.id.radioGroup_6_to_10));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void getViews() {
        mMeetingColor = findViewById(R.id.avatar);
        mMeetingSubject = findViewById(R.id.add_subject_input);
        mMeetingRoom = findViewById(R.id.add_room_name_layout);
        mMeetingDate = findViewById(R.id.add_date_input);
        mMeetingParticipants = findViewById(R.id.participants_list_text);
        mMeetingDescription = findViewById(R.id.add_description);
        mMeetingRoom1 = findViewById(R.id.radioButton_room1);
        mMeetingRoom2 = findViewById(R.id.radioButton_room2);
        mMeetingRoom3 = findViewById(R.id.radioButton_room3);
        mMeetingRoom4 = findViewById(R.id.radioButton_room4);
        mMeetingRoom5 = findViewById(R.id.radioButton_room5);
        mMeetingRoom6 = findViewById(R.id.radioButton_room6);
        mMeetingRoom7 = findViewById(R.id.radioButton_room7);
        mMeetingRoom8 = findViewById(R.id.radioButton_room8);
        mMeetingRoom9 = findViewById(R.id.radioButton_room9);
        mMeetingRoom10 = findViewById(R.id.radioButton_room10);
        mUpdateButton = findViewById(R.id.create);
    }

    private void setMeetingInfo(Meeting meeting) {
        mMeetingColor.setColorFilter(Color.parseColor(meeting.getAvatarColor()));
        mMeetingSubject.setText(meeting.getSubject());
        mMeetingDate.setText(meeting.getDate());
        mMeetingParticipants.setVisibility(View.VISIBLE);
        mMeetingParticipants.setText((CharSequence) meeting.getParticipants());
        mMeetingDescription.setText(meeting.getDescription());
        mUpdateButton.setText("MODIFIER");


    }

    private void setMeetingRoomChecked(Meeting meeting) {
        String roomName = meeting.getRoomName();
        switch (roomName) {
            case "Peach" : mMeetingRoom1.setChecked(true);
                break;
            case "Mario" : mMeetingRoom2.setChecked(true);
                break;
            case "Luigi" : mMeetingRoom3.setChecked(true);
                break;
            case "Yoshi" : mMeetingRoom4.setChecked(true);
                break;
            case "Toad" : mMeetingRoom5.setChecked(true);
                break;
            case "Toadette" : mMeetingRoom6.setChecked(true);
                break;
            case "Bowser" : mMeetingRoom7.setChecked(true);
                break;
            case "Koopa" : mMeetingRoom8.setChecked(true);
                break;
            case "Wario" : mMeetingRoom9.setChecked(true);
                break;
            case "Donkey Kong" : mMeetingRoom10.setChecked(true);
                break;
        }
    }

}
