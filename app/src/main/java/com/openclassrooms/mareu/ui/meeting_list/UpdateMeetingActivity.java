package com.openclassrooms.mareu.ui.meeting_list;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.MeetingApiService;

import butterknife.BindView;

public class UpdateMeetingActivity extends AppCompatActivity {

    private ImageView mMeetingColor;
    private EditText mMeetingSubject;
    private EditText mMeetingDate;
    private TextView mMeetingRoom;
    private TextView mMeetingParticipants;
    private EditText mMeetingDescription;
    private MeetingApiService mApiService;

    String updatedSubject;

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

    public void getViews() {
        mMeetingColor = findViewById(R.id.avatar);
        mMeetingSubject = findViewById(R.id.add_subject_input);
        mMeetingRoom = findViewById(R.id.add_room_name_layout);
        mMeetingDate = findViewById(R.id.add_date_input);
        mMeetingParticipants = findViewById(R.id.participants_list_text);
        mMeetingDescription = findViewById(R.id.add_description);
    }

    public void setMeetingInfo(Meeting meeting) {
        mMeetingColor.setColorFilter(Color.parseColor(meeting.getAvatarColor()));
        mMeetingSubject.setText(meeting.getSubject());
        mMeetingDate.setText(meeting.getDate());
        mMeetingParticipants.setVisibility(View.VISIBLE);
        mMeetingParticipants.setText((CharSequence) meeting.getParticipants());
        mMeetingDescription.setText(meeting.getDescription());
    }

}
