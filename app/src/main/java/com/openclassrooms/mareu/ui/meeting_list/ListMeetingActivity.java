package com.openclassrooms.mareu.ui.meeting_list;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.MeetingApiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListMeetingActivity extends AppCompatActivity {

    // UI Components
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.list_meetings)
    RecyclerView mRecyclerView;
    @BindView(R.id.meeting_list_empty_text)
    TextView emptyMeetingList;

    private final MeetingApiService mApiService = DI.getMeetingApiService();
    private MyMeetingRecyclerViewAdapter adapter;

    /**
     * Create menu with 3 options (filter by date / filter by room name / see all)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Handle item selection (filter by date / filter by room name / see all)
     */
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

    /**
     * Add all meetings to list in order to see full list)
     */
    private void resetFilter() {
        List<Meeting> mMeetingsList = mApiService.getMeetings();
        adapter.initList(mMeetingsList);
        mRecyclerView.setAdapter(adapter);
        showTextIfMeetingListEmpty(mMeetingsList);
    }

    /**
     * Launch a date dialog to see meetings according to selected date
     */
    private void dateDialog() {
        final Calendar currentDate = Calendar.getInstance(Locale.FRANCE);

        // Date Select Listener
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar cal = Calendar.getInstance(Locale.FRANCE);
                cal.set(i, i1, i2);
                List<Meeting> mMeetingsList = mApiService.getMeetingsFilteredListByDate(cal.getTime());
                adapter.initList(mMeetingsList);
                mRecyclerView.setAdapter(adapter);
                showTextIfMeetingListEmpty(mMeetingsList);
            }
        };

        // Create DatePickerDialog (Spinner Mode):
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                dateSetListener, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));

        // Show
        datePickerDialog.show();
    }

    /**
     * Launch a room selection dialog to see meetings according to selected room
     */
    private void roomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder( ListMeetingActivity.this );
        builder.setCancelable(false);
        builder.setTitle("Choisissez une salle de réunion :");

        // Set radio buttons with room names
        String[] items = {"Peach","Mario","Luigi","Yoshi","Toad","Toadette","Bowser","Koopa","Wario", "Donkey Kong"};
        int checkedItem = 0;
        final String[] rooms = new String[1];
        builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {

            // set selected room name in variable rooms and display a notification with room name
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ListMeetingActivity.this,"Salle \"" + items[which] + "\" sélectionnée",Toast.LENGTH_SHORT).show();
                rooms[0] = items[which];
            }
        });

        // When user click on "OK", display meetings or "list is empty" according to the search result
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Meeting> mMeetingsList = mApiService.getMeetingsListFilteredByRoomName((rooms[0].replace("[]", "")));
                adapter.initList(mMeetingsList);
                mRecyclerView.setAdapter(adapter);
                showTextIfMeetingListEmpty(mMeetingsList);
            }
        });

        // Cancel button
        builder.setNegativeButton("Retour", (dialog, which) -> dialog.cancel());

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();

        // Center DialogBox Button
        Button positiveButton = alertDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE);

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
    }

    /**
     * Show "No result found" if meeting list is empty
     */
    public void showTextIfMeetingListEmpty(List<Meeting> meetingList) {
        if (meetingList.size() == 0) {
            emptyMeetingList.setVisibility(View.VISIBLE);
        } else {
            emptyMeetingList.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setMeetingsList();
    }



    /**
     * Init meetings recyclerView
     */
    private void setMeetingsList() {
        List<Meeting> mMeetingsList = mApiService.getMeetings();
        adapter = new MyMeetingRecyclerViewAdapter(mMeetingsList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
        showTextIfMeetingListEmpty(mMeetingsList);
    }

    /**
     * Launch add meeting activity & close current
     */
    @OnClick(R.id.add_meeting)
    void addMeeting() {
        AddMeetingActivity.navigate(this);
        finish();
    }

    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, ListMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
