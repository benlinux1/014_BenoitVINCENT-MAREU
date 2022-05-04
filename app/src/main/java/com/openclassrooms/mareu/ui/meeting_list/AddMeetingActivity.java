package com.openclassrooms.mareu.ui.meeting_list;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.MeetingApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMeetingActivity extends AppCompatActivity {

    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.add_date_layout)
    TextInputLayout dateInput;
    @BindView(R.id.add_subject_layout)
    TextInputLayout subjectLayout;
    @BindView(R.id.add_subject_input)
    EditText subject;
    @BindView(R.id.add_participants_layout)
    TextInputLayout participantsLayout;
    @BindView(R.id.add_participants_input)
    EditText participantInput;
    @BindView(R.id.participants_list_title)
    TextView participantsListTitle;
    @BindView(R.id.participants_list_text)
    TextView participantsList;
    @BindView(R.id.add_description_layout)
    TextInputLayout descriptionInput;
    @BindView(R.id.radioButton_room1)
    RadioButton room1Button;
    @BindView(R.id.create)
    MaterialButton addButton;

    private MeetingApiService mApiService;
    private String mAvatarColor;
    private RadioGroup mFirstGroup;
    private RadioGroup mSecondGroup;
    private boolean isChecking = true;
    private int mCheckedId = R.id.radioButton_room1;
    private List<String> participants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getMeetingApiService();
        checkIfRoomIsChecked();
        setMeetingColor();
        checkIfSubjectIsValid(subjectLayout, subject, addButton);
        checkIfEmailIsValid(participantsLayout, participantInput, addButton);
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

    /**
     * Radio Group (1 & 2) listener to check if meeting room is selected
     */
    private void checkIfRoomIsChecked() {
        mFirstGroup = (RadioGroup) findViewById(R.id.radioGroup_1_to_5);
        mSecondGroup = (RadioGroup) findViewById(R.id.radioGroup_6_to_10);

        mFirstGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    mSecondGroup.clearCheck();
                    mCheckedId = checkedId;
                }
                isChecking = true;
            }
        });

        mSecondGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking) {
                    isChecking = false;
                    mFirstGroup.clearCheck();
                    mCheckedId = checkedId;
                }
                isChecking = true;
            }
        });
    }

    /**
     * Subject Text changed listener to validate field & set enabled the creation button
     */
    private void checkIfSubjectIsValid(TextInputLayout subjectInputLayout, EditText subjectEditText, MaterialButton actionButton) {
        subjectInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                actionButton.setEnabled(false);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                subjectEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.ACTION_DOWN)  {
                            validateSubject(s.toString(), subjectInputLayout);
                            if (validateSubject(s.toString(), subjectInputLayout)) {
                                actionButton.setEnabled(true);
                            }
                        }
                        return false;
                    }
                });
            }
        });
    }

    public static boolean validateSubject(String subject, TextInputLayout subjectError) {
        String subjectRegex = "^[A-Za-z ]*$";
        if (subject.matches(subjectRegex) && (subject.trim().length() > 2))  {
            return true;
        } else {
            subjectError.setError("Le sujet doit comporter au minimum 3 caractères");
            return false;
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
                        if ((actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.ACTION_DOWN) && (validateEmail(s.toString(), mailInputLayout))) {
                            addParticipant(s.toString());
                            showParticipantsList();
                            actionButton.setEnabled(true);
                        }
                        return false;
                    }
                });
            }
        });
    }

    public void addParticipant(String participant) {
        participants.add(participant);
        StringBuilder mails = new StringBuilder();
        for (String participantMail: participants) {
            mails.append(participantMail).append("; ");
        }
        participantsList.setText(mails.toString());
        participantInput.getText().clear();
        participantsLayout.setError(null);
    }

    public void showParticipantsList() {
        participantsListTitle.setVisibility(View.VISIBLE);
        participantsList.setVisibility(View.VISIBLE);
    }

    public static boolean validateEmail(String email, TextInputLayout inputError) {
        String emailRegex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (email.matches(emailRegex) && (email.trim().length() > 5))  {
            return true;
        } else {
            inputError.setError("Cette adresse e-mail ne semble pas valide");
            return false;
        }
    }

    /**
     * Set a random meeting color
     */
    private void setMeetingColor() {
        mAvatarColor = randomColor();
        avatar.setColorFilter(Color.parseColor(mAvatarColor));
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
     * Generate a random color
     * @return String
     */
    public String randomColor() {
        // create object of Random class
        Random obj = new Random();
        int rand_num = obj.nextInt(0xffffff + 1);
        // format it as hexadecimal string and print
        String colorCode = String.format("#%06x", rand_num);
        return colorCode;
    }

    private boolean validateAllFields() {
        if (subjectLayout.getEditText().getText().toString().equals("")) {
            subjectLayout.setError("Merci de compléter ce champ");
        }
        else if (dateInput.getEditText().getText().toString().equals("")) {
            dateInput.setError("Merci de compléter ce champ");
        }
        else if (participantsList.getVisibility() == View.GONE) {
            participantsLayout.setError("Merci de compléter ce champ");
        }
        else if (descriptionInput.getEditText().getText().toString().equals("")) {
            descriptionInput.setError("Merci de compléter ce champ");
        } else {
            return true;
        }
        return false;
    }

    /**
     * Send form values to create a new meeting
     */
    @OnClick(R.id.create)
    void addMeeting() {
        Meeting meeting = new Meeting(
                System.currentTimeMillis(),
                subjectLayout.getEditText().getText().toString(),
                mAvatarColor,
                dateInput.getEditText().getText().toString(),
                getRoomValue(),
                participantsList.getText().toString(),
                descriptionInput.getEditText().getText().toString()
        );
        if (validateAllFields()) {
            mApiService.createMeeting(meeting);
            finish();
        }
    }

    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
