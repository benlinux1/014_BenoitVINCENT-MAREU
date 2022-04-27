package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Meeting;
import com.openclassrooms.entrevoisins.service.MeetingApiService;

public class MeetingDetailsActivity extends AppCompatActivity {

    private FloatingActionButton mUpdateButton;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getMeetingApiService();

        /**
         * Set Meeting informations
         */
        setMeetingData();
        Meeting meeting = setMeetingData();

        /**
         * Listener on Favorite's Button to add/remove neighbour in/from Favorites List
         * Modify the favorite's button design (empty / full) according to the situation too
         */
        mUpdateButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Toggle neighbour's favorite attribute
             * Toggle the favorite's button design (empty / full) according to the situation
             * Displays alertDialog Box according to the situation
             * */
            @Override
            public void onClick(View view) {
                mApiService.toggleFree(meeting);
                mUpdateButton.setImageResource(meeting.isFree()?R.drawable.ic_star_yellow_24 : R.drawable.ic_favorite_empty);
                createCustomDialogBox(meeting.isFree()? meeting.getRoomName() + " a été ajouté(e) à vos favoris" : meeting.getRoomName() + " a été supprimé(e) de vos favoris");
            }
       });
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
     * Set all meeting informations in the right fields
     */
    public Meeting setMeetingData() {

        Intent getProfileIntent = getIntent();
        long id = getProfileIntent.getLongExtra("MEETING_ID",-1);
        Meeting meeting = mApiService.getMeetingData(id);

        getViews();
        setMeetingColor(meeting);
        setMeetingInfo(meeting);
        setUpdateButton(meeting);

        return meeting;
    }

    public void getViews() {
        mMeetingColor = findViewById(R.id.meeting_detail_avatar);
        mMeetingSubtitle = findViewById(R.id.meeting_detail_second_title);
        mMeetingRoom = findViewById(R.id.meeting_detail_location_room);
        mMeetingDate = findViewById(R.id.meeting_detail_date);
        mMeetingParticipants = findViewById(R.id.meeting_detail_participants_list);
        mMeetingDescription = findViewById(R.id.meeting_detail_description);
    };

    public void setMeetingColor(Meeting meeting) {
        Glide.with(mMeetingColor.getContext())
                .load(R.mipmap.ic_mario_foreground)
                .centerCrop()
                .into(mMeetingColor);
    }

    public void setMeetingInfo(Meeting meeting) {
        mMeetingSubtitle.setText(meeting.getSubject());
        mMeetingDate.setText(meeting.getDate());
        mMeetingRoom.setText("Salle " + meeting.getRoomName());
        mMeetingParticipants.setText(meeting.getParticipants());
        mMeetingDescription.setText(meeting.getDescription());
    };

    public void setUpdateButton(Meeting meeting) {
        mUpdateButton = findViewById(R.id.meeting_detail_update_button);
        // Set Favorite Button color according to the situation
        mUpdateButton.setImageResource(meeting.isFree() ? R.drawable.ic_star_yellow_24 : R.drawable.ic_favorite_empty);

    }

    /**
     * Displays an alert dialog box for best user XP ;)
     */
    public void createCustomDialogBox(String message) {
        // Build an alert dialogBox
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MeetingDetailsActivity.this);
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();

        // Center DialogBox Button
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout parent = (LinearLayout) positiveButton.getParent();
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        View leftSpacer = parent.getChildAt(1);
        leftSpacer.setVisibility(View.GONE);

        // Center DialogBox Message
        TextView messageText = (TextView) alertDialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
    }

}