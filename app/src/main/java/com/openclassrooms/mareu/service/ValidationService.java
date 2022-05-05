package com.openclassrooms.mareu.service;

import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioGroup;
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

    /**
     * Subject Text changed listener to validate field & set enabled the creation button
     */
    public static void checkIfSubjectIsValid(TextInputLayout subjectInputLayout, EditText subjectEditText, MaterialButton actionButton) {
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
                            ValidationService.validateSubject(s.toString(), subjectInputLayout);
                            if (ValidationService.validateSubject(s.toString(), subjectInputLayout)) {
                                actionButton.setEnabled(true);
                            }
                        }
                        return false;
                    }
                });
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
