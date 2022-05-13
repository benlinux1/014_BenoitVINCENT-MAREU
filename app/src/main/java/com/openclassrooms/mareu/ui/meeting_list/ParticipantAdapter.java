package com.openclassrooms.mareu.ui.meeting_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.events.DeleteMeetingEvent;
import com.openclassrooms.mareu.events.DeleteParticipantEvent;
import com.openclassrooms.mareu.model.Participant;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;


public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ViewHolder> {

    private final ArrayList<Participant> participants;

    public ParticipantAdapter(ArrayList<Participant> participants) {
        this.participants = participants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_participant, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Participant participant = participants.get(position);
        viewHolder.displayParticipant(participant);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return participants.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView email;
        public final ImageButton deleteParticipantButton;

        public ViewHolder(View view) {
            super(view);
            email = view.findViewById(R.id.participant_email);
            deleteParticipantButton = view.findViewById(R.id.delete_participant_button);
        }

        public void displayParticipant(Participant participant) {
            email.setText(participant.email);
        }
    }
}