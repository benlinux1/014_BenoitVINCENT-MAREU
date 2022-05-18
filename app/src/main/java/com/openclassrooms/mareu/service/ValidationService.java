package com.openclassrooms.mareu.service;

import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;


public class ValidationService {

    public static boolean validateTextInput(String subject, TextInputLayout subjectError) {
        String subjectRegex = "^[a-zA-Z0-9éèàêîïôö ]*$";
        if (subject.matches(subjectRegex) && (subject.trim().length() > 2))  {
            subjectError.setError(null);
            return true;
        } else {
            subjectError.setError("Le sujet doit comporter au minimum 3 caractères");
            return false;
        }
    }

    public static boolean validateEmail(String email, TextInputLayout inputError) {
        String emailRegex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (email.matches(emailRegex) && (email.trim().length() > 5))  {
            inputError.setError(null);
            return true;
        } else {
            inputError.setError("Cette adresse e-mail ne semble pas valide");
            return false;
        }
    }

    public static boolean validateAllFields(TextInputLayout subjectLayout, RecyclerView participantsList, TextInputLayout participantsLayout, TextInputLayout descriptionInput) {
        if (subjectLayout.getEditText().getText().toString().equals("")) {
            subjectLayout.setError("Merci de compléter ce champ");
        }
        else if (participantsList.toString().equals("")) {
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
     * Subject Text changed listener to validate field & set enabled/disabled the creation button
     */
    public static void textInputValidation(EditText textInput, TextInputLayout textInputLayout, MaterialButton actionButton) {
        textInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (ValidationService.validateTextInput(textInputLayout.getEditText().getText().toString(), textInputLayout)) {
                        textInputLayout.setError(null);
                        actionButton.setEnabled(true);
                    } else {
                        actionButton.setEnabled(false);
                    }
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
    }

    /**
     * Radio Group (1 & 2) listener to clear one of them when meeting room is selected
     */
    public static void checkIfRoomIsChecked(RadioGroup mFirstGroup, RadioGroup mSecondGroup) {
        final boolean[] isChecking = {true};
        final int[] mCheckedId = new int[1];

        mFirstGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking[0]) {
                    isChecking[0] = false;
                    mSecondGroup.clearCheck();
                    mCheckedId[0] = checkedId;
                }
                isChecking[0] = true;
            }
        });

        mSecondGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isChecking[0]) {
                    isChecking[0] = false;
                    mFirstGroup.clearCheck();
                    mCheckedId[0] = checkedId;
                }
                isChecking[0] = true;
            }
        });
    }
}
