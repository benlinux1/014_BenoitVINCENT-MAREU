package com.openclassrooms.mareu.ui.meeting_list;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.Participant;
import com.openclassrooms.mareu.utility.DateTimeUtility;
import com.openclassrooms.mareu.service.MeetingApiService;
import com.openclassrooms.mareu.utility.ValidationUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
    private long mMeetingId;
    private final ArrayList<Participant> arrayOfParticipants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Meeting meeting = getMeetingInfo();
        getViews();
        setMeetingInfo(meeting);
        initParticipantsRecyclerView();
        showParticipantsList();
        ValidationUtility.checkIfRoomIsChecked(findViewById(R.id.radioGroup_1_to_5), findViewById(R.id.radioGroup_6_to_10));
        setMeetingRoomChecked(meeting);
        ValidationUtility.textInputValidation(mMeetingSubject, mMeetingSubjectLayout, mUpdateButton);
        ValidationUtility.textInputValidation(mMeetingDescription, mMeetingDescriptionLayout, mUpdateButton);
        checkIfEmailIsValid(mParticipantsLayout, mParticipantInput, mUpdateButton);
        listenToDate();
        setUpdateButtonListener();
    }

    /**
     * Close form and go to meeting details if back button is clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                Meeting meeting = getMeetingInfo();
                finish();
                Intent meetingDetailActivityIntent = new Intent(UpdateMeetingActivity.this, MeetingDetailsActivity.class);
                meetingDetailActivityIntent.putExtra("MEETING_ID", meeting.getId());
                UpdateMeetingActivity.this.startActivity(meetingDetailActivityIntent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Get meeting informations according to the ID passed from Intent
     */
    private Meeting getMeetingInfo() {
        MeetingApiService mApiService = DI.getMeetingApiService();
        Intent getProfileIntent = getIntent();
        mMeetingId = getProfileIntent.getLongExtra("MEETING_ID",-1);
        Meeting meeting = mApiService.getMeetingData(mMeetingId);
        return meeting;
    }

    /**
     * Get all form views
     */
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
        mMeetingParticipantsListTitle = findViewById(R.id.participants_list_title);
        mMeetingParticipants = findViewById(R.id.participants_list_text);
        mUpdateButton = findViewById(R.id.create);
    }

    /**
     * Pre complete fields with meeting informations
     */
    private void setMeetingInfo(Meeting meeting) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
        mMeetingColor.setColorFilter(Color.parseColor(meeting.getAvatarColor()));
        mMeetingSubject.setText(meeting.getSubject());
        mMeetingDate.setText(dateFormat.format(meeting.getDate()));
        mMeetingDescription.setText(meeting.getDescription());
        mUpdateButton.setText("MODIFIER");
        mUpdateButton.setEnabled(true);
        setParticipants(meeting);
    }

    /**
     * Set date & time in date field when user pick a date in calendar
     */
    private void listenToDate() {
        mMeetingDate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                DateTimeUtility.setDate(mMeetingDate, UpdateMeetingActivity.this);
            }
        });
    }

    /**
     * Check the right radio button according to original meeting room
     */
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

    /**
     * Email validation with text changed listener when user press "Done" on keyboard in order to add new participant
     */
    private void checkIfEmailIsValid(TextInputLayout mailInputLayout, EditText mailEditText, MaterialButton actionButton) {
        mailInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                mailEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (ValidationUtility.validateEmail(s.toString(), mailInputLayout))) {
                            Participant participant = new Participant(s.toString());
                            addParticipant(participant);
                        }
                        return false;
                    }
                });
            }
        });
    }

    /**
     * Display a custom recycler view for participants
     */
    private void initParticipantsRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.participants_list_text);
        recyclerView.setLayoutManager(layoutManager);
        ParticipantRecyclerViewAdapter mAdapter = new ParticipantRecyclerViewAdapter(arrayOfParticipants);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * Format participants list for participants recycler view
     */
    public void setParticipants(Meeting meeting) {
        String emailList = meeting.getParticipants();
        String[] emails = emailList.split("; ");
        for (String email : emails) {
            Participant participantMail = new Participant(email);
            arrayOfParticipants.add(participantMail);
        }
    }

    /**
     * Show participants list and title "E-mail des participants"
     */
    public void showParticipantsList() {
        mMeetingParticipantsListTitle.setVisibility(View.VISIBLE);
        mMeetingParticipants.setVisibility(View.VISIBLE);
    }

    /**
     * Add the participant email in custom recycler view when added in selected input
     */
    public void addParticipant(Participant participant) {
        initParticipantsRecyclerView();
        arrayOfParticipants.add(participant);
        mParticipantInput.getText().clear();
        mParticipantsLayout.setError(null);
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.meeting_fields_page), "Participant ajouté avec succès", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    /**
     * Check if Participants List is not Empty (Used when Create Button is pressed)
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

    private void setUpdateButtonListener() {
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateMeeting();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Send form values to model in order to update meeting after fields validation. Then close activity.
     */
    void updateMeeting() throws ParseException {
        if (checkIfParticipantListIsNotEmpty() && ValidationUtility.validateAllFields(mMeetingSubjectLayout, mMeetingParticipants, mParticipantsLayout, mMeetingDescriptionLayout)) {
            mMeetingId = getMeetingInfo().getId();
            mApiService = DI.getMeetingApiService();
            // loop in meetings list
            for (Meeting meeting :  mApiService.getMeetings()) {
                // when meeting is found
                if (meeting.getId() == mMeetingId) {
                    // check if room is free at the selected date & replace meeting data
                    if (mApiService.checkIfRoomIsFree(DateTimeUtility.getDate(mMeetingDateLayout.getEditText().getText().toString()), getRoomValue(), mMeetingId)) {
                        meeting.setDate(DateTimeUtility.getDate(mMeetingDateLayout.getEditText().getText().toString()));
                        meeting.setSubject(mMeetingSubjectLayout.getEditText().getText().toString());
                        meeting.setRoomName(getRoomValue());
                        meeting.setParticipants(getEmailList());
                        meeting.setDescription(mMeetingDescriptionLayout.getEditText().getText().toString());
                        // Then close activity, notify user with success toast & back to meeting details page
                        finish();
                        Toast.makeText(UpdateMeetingActivity.this, "Vos modifications ont bien été enregistrées", Toast.LENGTH_LONG).show();
                        Intent meetingDetailsActivityIntent = new Intent(UpdateMeetingActivity.this, MeetingDetailsActivity.class);
                        meetingDetailsActivityIntent.putExtra("MEETING_ID", mMeetingId);
                        UpdateMeetingActivity.this.startActivity(meetingDetailsActivityIntent);
                    } else { // Notify user if room isn't free at the selected date
                        Toast.makeText(UpdateMeetingActivity.this, "La salle " + getRoomValue() + " est occupée à cette date", Toast.LENGTH_LONG).show();
                    }
                    break;
                }
            }
        }
    }
}
