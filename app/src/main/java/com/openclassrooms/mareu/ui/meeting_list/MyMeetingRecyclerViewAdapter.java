package com.openclassrooms.mareu.ui.meeting_list;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.events.DeleteMeetingEvent;
import com.openclassrooms.mareu.model.Meeting;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder> {

    private final List<Meeting> mMeetings;
    private final String actualPage;

    public MyMeetingRecyclerViewAdapter(List<Meeting> items, String actualPage) {
        mMeetings = items;
        this.actualPage = actualPage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);
        holder.mMeetingSubject.setText(meeting.getSubject() + " -");
        holder.mMeetingDate.setText(meeting.getDate() + " -");
        holder.mMeetingRoom.setText(meeting.getRoomName());
        holder.mMeetingParticipants.setText((CharSequence) meeting.getParticipants());
        holder.mMeetingAvatar.setColorFilter(Color.parseColor(meeting.getAvatarColor()));

        // Delete meeting Button
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete meeting from global List
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
                Toast.makeText(v.getContext(), "La réunion intitulée \"" + meeting.getSubject() + "\" a bien été supprimée", Toast.LENGTH_LONG).show();
            }
        });

         // Launch Meeting Details according to the Meeting Id
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View meetingItem) {
                Intent meetingDetailActivityIntent = new Intent(meetingItem.getContext(), MeetingDetailsActivity.class);
                meetingDetailActivityIntent.putExtra("MEETING_ID", meeting.getId());
                meetingItem.getContext().startActivity(meetingDetailActivityIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mMeetingAvatar;
        @BindView(R.id.item_list_subject)
        public TextView mMeetingSubject;
        @BindView(R.id.item_list_date)
        public TextView mMeetingDate;
        @BindView(R.id.item_list_room)
        public TextView mMeetingRoom;
        @BindView(R.id.item_list_participants)
        public TextView mMeetingParticipants;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
