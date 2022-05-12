package com.openclassrooms.mareu.ui.meeting_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.Participant;

import java.util.ArrayList;
import java.util.List;


public class ParticipantsAdapter extends ArrayAdapter<Participant> {

    public ParticipantsAdapter(Context context, ArrayList<Participant> participants) {
        super(context, 0, participants);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Participant participant = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_participant, parent, false);
        }

        // Lookup view for data population
        TextView participantEmail = (TextView) convertView.findViewById(R.id.participant_email);
        ImageView deleteParticipantButton = (ImageView) convertView.findViewById(R.id.delete_participant_button);

        // Populate with participant data
        participantEmail.setText(participant.email);

        // Return the completed view to render on screen
        return convertView;
    }

}

