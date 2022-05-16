package com.openclassrooms.mareu.ui.meeting_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Participant;

import java.util.ArrayList;


public class ParticipantRecyclerViewAdapter extends RecyclerView.Adapter<ParticipantRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Participant> participants;

    public ParticipantRecyclerViewAdapter(ArrayList<Participant> participants) {
        this.participants = participants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_participant, parent, false);

        return new ViewHolder(view).linkAdapter(this);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Participant participant = participants.get(position);
        viewHolder.displayParticipant(participant);
    }

    // Return the size of the array of participants (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return participants.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView participantEmail;

        private ParticipantRecyclerViewAdapter adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            participantEmail = itemView.findViewById(R.id.participant_email);
            itemView.findViewById(R.id.delete_participant_button).setOnClickListener(view -> {
                adapter.participants.remove(getAdapterPosition());
                adapter.notifyItemRemoved(getAdapterPosition());
            });
        }

        // Display participant email in viewHolder
        public void displayParticipant(Participant participant) {
            participantEmail.setText(participant.email);
        }

        public ViewHolder linkAdapter(ParticipantRecyclerViewAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }
}