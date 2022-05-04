package com.openclassrooms.mareu.service;

import android.support.design.widget.TextInputLayout;

import android.widget.TextView;


public class ValidationService {

    public static boolean validateSubject(String subject, TextInputLayout subjectError) {
        String subjectRegex = "^[A-Za-z ]*$";
        if (subject.matches(subjectRegex) && (subject.trim().length() > 2))  {
            return true;
        } else {
            subjectError.setError("Le sujet doit comporter au minimum 3 caractères");
            return false;
        }
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

    public static boolean validateAllFields(TextInputLayout subjectLayout, TextInputLayout dateInput, TextView participantsList, TextInputLayout participantsLayout, TextInputLayout descriptionInput) {
        if (subjectLayout.getEditText().getText().toString().equals("")) {
            subjectLayout.setError("Merci de compléter ce champ");
        }
        else if (dateInput.getEditText().getText().toString().equals("")) {
            dateInput.setError("Merci de compléter ce champ");
        }
        else if (participantsList.getText().toString().equals("")) {
            participantsLayout.setError("Merci de compléter ce champ");
        }
        else if (descriptionInput.getEditText().getText().toString().equals("")) {
            descriptionInput.setError("Merci de compléter ce champ");
        } else {
            return true;
        }
        return false;
    }
}
