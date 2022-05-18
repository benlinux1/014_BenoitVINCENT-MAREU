package com.openclassrooms.mareu.ui.meeting_list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.MeetingApiService;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class MeetingDetailsActivity extends AppCompatActivity {

    private FloatingActionButton mEditButton;
    private ImageView mMeetingColor;
    private TextView mMeetingSubtitle;
    private TextView mMeetingDate;
    private TextView mMeetingRoom;
    private TextView mMeetingParticipants;
    private TextView mMeetingDescription;
    private MeetingApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getMeetingApiService();

        setMeetingData();

        // Listener on Update Button to launch alert box & confirm update
        mEditButton.setOnClickListener(view -> createCustomDialogBox(" Voulez vous vraiment modifier cette réunion ?"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set all meeting informations in the right fields
     */
    public Meeting setMeetingData() {

        Intent getProfileIntent = getIntent();
        long id = getProfileIntent.getLongExtra("MEETING_ID",-1);
        Meeting meeting = mApiService.getMeetingData(id);

        getViews();
        setMeetingBackground();
        setMeetingInfo(meeting);
        setEditButton();

        return meeting;
    }

    public void getViews() {
        mMeetingColor = findViewById(R.id.meeting_detail_avatar);
        mMeetingSubtitle = findViewById(R.id.meeting_detail_second_title);
        mMeetingRoom = findViewById(R.id.meeting_detail_location_room);
        mMeetingDate = findViewById(R.id.meeting_detail_date);
        mMeetingParticipants = findViewById(R.id.meeting_detail_participants_list);
        mMeetingDescription = findViewById(R.id.meeting_detail_description);
    }

    public void setMeetingBackground() {
        Glide.with(mMeetingColor.getContext())
            .load(R.mipmap.ic_mario_foreground)
            .centerCrop()
            .into(mMeetingColor);
    }

    public void setMeetingInfo(Meeting meeting) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy à HH:mm");
        mMeetingSubtitle.setText(meeting.getSubject());
        mMeetingDate.setText("Le " + dateFormat.format(meeting.getDate()));
        mMeetingRoom.setText("Salle " + "\"" + meeting.getRoomName() + "\"");
        mMeetingParticipants.setText((CharSequence) meeting.getParticipants());
        mMeetingDescription.setText(meeting.getDescription());
    }

    public void setEditButton() {
        mEditButton = findViewById(R.id.meeting_detail_update_button);
    }

    /**
     * Displays an alert dialog box for best user XP ;)
     */
    public void createCustomDialogBox(String message) {
        // Build an alert dialogBox
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MeetingDetailsActivity.this);
        builder.setCancelable(false);
        builder.setMessage(message);

        builder.setPositiveButton("OUI", (dialog, which) -> {
            Meeting meeting = setMeetingData();
            finish();
            Intent updateMeetingActivityIntent = new Intent(MeetingDetailsActivity.this, UpdateMeetingActivity.class);
            updateMeetingActivityIntent.putExtra("MEETING_ID", meeting.getId());
            MeetingDetailsActivity.this.startActivity(updateMeetingActivityIntent);
        });

        builder.setNegativeButton("NON", (dialog, which) -> dialog.cancel());
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();

        // Center DialogBox Button
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // Set layout & buttons margin
        params.setMargins(40,0,0,0);
        positiveButton.setLayoutParams(params);

        // Center buttons removing original leftSpacer
        LinearLayout parent = (LinearLayout) positiveButton.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);

        // Center DialogBox Message
        TextView messageText = (TextView) alertDialog.findViewById(android.R.id.message);
        assert messageText != null;
        messageText.setGravity(Gravity.CENTER);
    }

}