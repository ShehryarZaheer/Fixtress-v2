package com.example.ehsanullah.loginandregistration.neutrocism;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.emotions_detection.EmotionsDetectionActivity;
import com.example.ehsanullah.loginandregistration.login_and_registration.User;
import com.example.ehsanullah.loginandregistration.questionnnaire_results.filledQuestionnaireLevel;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Ehsan Ullah on 4/2/2017.
 */

public class Question8 extends Fragment {

    boolean depression = false, getStressedEasily = false, emotionallyStable = false, fearless = false, shamelessness = false, negativeFeelings = false;
    SharedPreferences sharedPreferences;
    String q8;

    public Question8(String q8) {
        this.q8 = q8;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.neutrocism_question_8, container, false);


        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);


        TextView tvQuestion8 = (TextView) view.findViewById(R.id.tvQuestion8);
        tvQuestion8.setText(q8);


        RadioButton rbRarelySad = (RadioButton) view.findViewById(R.id.rbRarelySad);
        rbRarelySad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //___________ opening the EmotiosDetectoinActivity ___________________
                Intent intent = new Intent(getActivity(), EmotionsDetectionActivity.class);
                startActivity(intent);


                Toast.makeText(getActivity(), "thank you for your nice behavoiur.", Toast.LENGTH_SHORT).show();

                Constant.neutrocismAnswersList.add(7, "0");//reversed
                Constant.NUM_OF_ATTEMPTED_QUESTIONS += 1;

                measuringAndStoringTheResultsInFirebaseFunc();
            }
        });

        RadioButton rbOftenSad = (RadioButton) view.findViewById(R.id.rbOftenSad);
        rbOftenSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //___________ opening the EmotiosDetectoinActivity ___________________
                Intent intent = new Intent(getActivity(), EmotionsDetectionActivity.class);
                startActivity(intent);



                Toast.makeText(getActivity(), "thank you for your nice behaviour.", Toast.LENGTH_SHORT).show();

                Constant.neutrocismAnswersList.add(7, "1");//reversed
                Constant.NUM_OF_ATTEMPTED_QUESTIONS += 1;

                measuringAndStoringTheResultsInFirebaseFunc();
            }
        });


        return view;
    }

    private void measuringAndStoringTheResultsInFirebaseFunc() {

        //check how much answers are no and how much are yes
        int zeros = countNumberOfZerosOrOnes(Constant.neutrocismAnswersList, "0");
        int ones = countNumberOfZerosOrOnes(Constant.neutrocismAnswersList, "1");

        //measuring the nature of person
        measuringNeutrocismResults(zeros, ones);
    }


    private void measuringNeutrocismResults(int zeros, int ones) {

        String results = "";
        if (ones > zeros) {
            results = "depresivity, hopelessness,emotionally reactive and vulnerable to stress ";

            depression = true;
            getStressedEasily = true;
            emotionallyStable = false;
            fearless = false;
            shamelessness = false;
            negativeFeelings = false;

        } else if (ones < zeros) {
            results = "fearless and shamelessness,less easily upset and are less emotionally reactive,calm, emotionally stable, and " +
                    "free from persistent negative feelings";

            depression = false;
            getStressedEasily = false;
            emotionallyStable = true;
            fearless = true;
            shamelessness = true;
            negativeFeelings = true;
        } else if (ones == zeros) {
            results = "";
        }


        storeResultsInFirebaseFunc();
    }

    private void storeResultsInFirebaseFunc() {

        //______________________________ Storing the agreeableness results on current user's pushed questionnaire_ID _____________________________
        Firebase currentIdRef = Constant.FIREBASE_REF.child("questionnaireResults").child(Constant.CURRENT_USER_QUESTIONNAIRE_ID);

        currentIdRef.child("depression").setValue(depression);
        currentIdRef.child("getStressedEasily").setValue(getStressedEasily);
        currentIdRef.child("emotionallyStable").setValue(emotionallyStable);
        currentIdRef.child("fearless").setValue(fearless);
        currentIdRef.child("shamelessness").setValue(shamelessness);
        currentIdRef.child("negativeFeelings").setValue(negativeFeelings);
        currentIdRef.child("numOfAttemptedQuestions").setValue(Constant.NUM_OF_ATTEMPTED_QUESTIONS);


        //______________________________ Storing that how much questionnaire has been filled _____________________________
        currentIdRef.child("filledQuestionnaireLevel")
                .setValue(new filledQuestionnaireLevel(true, true, true, true, true));


        //_______________________________ Query to get the all the data of a specific user w.r.t Email _________________________________
        Query mobileNumExistanceQuery = Constant.FIREBASE_REF.child("users").orderByChild("email").equalTo(Constant.CURRENT_USERS_EMAIL);
        mobileNumExistanceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = null;
                String currentUserId = "";


                //_______________________________ check if server return null _________________________________
                if (dataSnapshot.exists()) {
                    //getting the data at specific node which is selected by input Email
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        currentUserId = child.getKey().toString();
                    }

                    //_______________________________ Storing in Firebase that questionnaire has been filled _________________________________
                    Constant.FIREBASE_REF.child("users").child(currentUserId).child("questionnaireFilled").setValue(true);

                    //_______________________________ Storing in sharedPreferences that questionnaire has been filled _________________________________
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("questionnaire_filled", true);
                    editor.commit();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getActivity(), firebaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int countNumberOfZerosOrOnes(ArrayList<String> answersList, String answer) {
        int count = 0;
        for (String i : answersList) {
            if (i.equals(answer)) {
                count++;
            }
        }
        return count;
    }
}

