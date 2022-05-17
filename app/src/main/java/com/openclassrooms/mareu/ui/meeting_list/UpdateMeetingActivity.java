package com.openclassrooms.mareu.ui.meeting_list;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.Participant;
import com.openclassrooms.mareu.service.MeetingApiService;
import com.openclassrooms.mareu.service.ValidationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.OnClick;

public class UpdateMeetingActivity extends AppCompatActivity {

    private TextInputLayout mMeetingDateLayout;
    private EditText mMeetingDate;
    private TextInputLayout mMeetingSubjectLayout;
    private EditText mMeetingSubject;
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
    private TextInputLayout mParticipantsLayout;
    private EditText mParticipantInput;
    private TextView mMeetingParticipantsListTitle;
    private RecyclerView mMeetingParticipants;
    private TextInputLayout mMeetingDescriptionLayout;
    private EditText mMeetingDescription;
    private ImageView mMeetingColor;
    private MeetingApiService mApiService;
    private MaterialButton mUpdateButton;
    private ImageButton mDeleteButton;
    private TextView mMeetingFirstSubject;

    private final ArrayList<Participant> arrayOfParticipants = new ArrayList<>();

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
        initRecyclerView();
        showParticipantsList();
        setMeetingRoomChecked(meeting);
        ValidationService.checkIfRoomIsChecked(findViewById(R.id.radioGroup_1_to_5), findViewById(R.id.radioGroup_6_to_10));
        ValidationService.textInputValidation(mMeetingSubject, mMeetingSubjectLayout, mUpdateButton);
        ValidationService.textInputValidation(mMeetingDescription, mMeetingDescriptionLayout, mUpdateButton);
        checkIfEmailIsValid(mParticipantsLayout, mParticipantInput, mUpdateButton);
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
        mMeetingSubjectLayout = findViewById(R.id.add_subject_layout);
        mMeetingSubject = findViewById(R.id.add_subject_input);
        mMeetingDateLayout = findViewById(R.id.add_date_layout);
        mMeetingDate = findViewById(R.id.add_date_input);
        mParticipantsLayout = findViewById(R.id.add_participants_layout);
        mParticipantInput = findViewById(R.id.add_participants_input);
        mMeetingDescriptionLayout = findViewById(R.id.add_description_layout);
        mMeetingDescription = findViewById(R.id.add_description);
        mFirstGroup = findViewById(R.id.radioGroup_1_to_5);
        mSecondGroup = findViewById(R.id.radioGroup_6_to_10);
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
        mDeleteButton = findViewById(R.id.item_list_delete_button);
        mMeetingParticipantsListTitle = findViewById(R.id.participants_list_title);
        mMeetingParticipants = findViewById(R.id.participants_list_text);
        mMeetingFirstSubject = findViewById(R.id.meeting_id);
    }

    private void setMeetingInfo(Meeting meeting) {
        mMeetingColor.setColorFilter(Color.parseColor(meeting.getAvatarColor()));
        mMeetingSubject.setText(meeting.getSubject());
        mMeetingDate.setText(meeting.getDate());
        mMeetingDescription.setText(meeting.getDescription());
        mUpdateButton.setText("MODIFIER");
        setParticipants(meeting);
        mMeetingFirstSubject.setText(meeting.getSubject());
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

    private void checkIfEmailIsValid(TextInputLayout mailInputLayout, EditText mailEditText, MaterialButton actionButton) {
        mailInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                actionButton.setEnabled(false);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                mailEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.ACTION_DOWN) && (ValidationService.validateEmail(s.toString(), mailInputLayout))) {
                            Participant participant = new Participant(s.toString());
                            addParticipant(participant);
                            actionButton.setEnabled(true);
                        }
                        return false;
                    }
                });
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.participants_list_text);
        recyclerView.setLayoutManager(layoutManager);

        ParticipantRecyclerViewAdapter mAdapter = new ParticipantRecyclerViewAdapter(arrayOfParticipants);

        // Set CustomAdapter as the adapter for RecyclerView
        recyclerView.setAdapter(mAdapter);
    }

    public void setParticipants(Meeting meeting) {
        String emailList = meeting.getParticipants();
        String[] emails = emailList.split("; ");
        for (String email : emails) {
            Participant participantMail = new Participant(email);
            arrayOfParticipants.add(participantMail);
        }

    }

    public void showParticipantsList() {
        mMeetingParticipantsListTitle.setVisibility(View.VISIBLE);
        mMeetingParticipants.setVisibility(View.VISIBLE);
    }

    /**
     * Add the participant email when added in selected input
     */
    public void addParticipant(Participant participant) {
        initRecyclerView();
        arrayOfParticipants.add(participant);
        mParticipantInput.getText().clear();
        mParticipantsLayout.setError(null);
    }

    /**
     * Check if Participants List is not Empty
     * Used when Create Button is pressed
     */
    private boolean checkIfParticipantListIsNotEmpty() {
        if (arrayOfParticipants.isEmpty()) {
            mParticipantsLayout.setError("Merci de saisir l'adresse email d'un participant");
            mMeetingParticipantsListTitle.setVisibility(View.GONE);
            return false;
        }
        return true;
    }

    /**
     * Used to get the room name checked in the list
     */
    private String getRoomValue() {
        int selectedRadioButtonIDinGroup1 = mFirstGroup.getCheckedRadioButtonId();
        int selectedRadioButtonIDinGroup2 = mSecondGroup.getCheckedRadioButtonId();

        // If nothing is selected from Radio Group, then it return -1
        if (selectedRadioButtonIDinGroup1 != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonIDinGroup1);
            String selectedRadioButtonText1 = selectedRadioButton.getText().toString();
            return selectedRadioButtonText1;
        } else { // it means that button is checked in Group 2
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonIDinGroup2);
            String selectedRadioButtonText2 = selectedRadioButton.getText().toString();
            return selectedRadioButtonText2;
        }
    }

    /**
     * Convert array of participants to email List
     * @return String emailList
     */
    public String getEmailList() {
        StringBuilder emailList = new StringBuilder();
        for (Participant participant : arrayOfParticipants) {
            String mail = participant.email;
            emailList.append(mail+"; ");
        }
        return emailList.toString();
    }


    /**
     * Send form values to create a new meeting after fields validation & close add activity
     * @return
     */
    @OnClick(R.id.create)
    void updateMeeting() {
        if (checkIfParticipantListIsNotEmpty() && ValidationService.validateAllFields(mMeetingSubjectLayout, mMeetingDateLayout, mMeetingParticipants, mParticipantsLayout, mMeetingDescriptionLayout)) {
        }
    }
}
