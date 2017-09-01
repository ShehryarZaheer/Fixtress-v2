package com.example.ehsanullah.loginandregistration.openness;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.questionnnaire_results.filledQuestionnaireLevel;
import com.firebase.client.Firebase;

import java.util.ArrayList;

/**
 * Created by Ehsan Ullah on 3/30/2017.
 */

public class Question6 extends Fragment {

    private Boolean sensitiveToBeauty = false, willingToTryNewThings = false,
            creative = false, exploringNewCulture = false, poetry = false, art = false;
    private String q6;
    private SharedPreferences sharedPreferences;

    public Question6(String q6) {
        this.q6 = q6;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.openness_question_6, container, false);

        TextView tvQuestion6 = (TextView) view.findViewById(R.id.tvQuestion6);
        tvQuestion6.setText(q6);


        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);


        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.opennessAnswersList.add(5, "1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;

                measuringAndStoringTheResultsInFirebaseFunc();


                Toast.makeText(getActivity(), "then i bet you will be a hero in your field...", Toast.LENGTH_SHORT).show();

                //for conscientiousness start
                Toast.makeText(getActivity(), "Lets talk about your living style in home.", Toast.LENGTH_SHORT).show();

                //String q1 = "do you like things in order?";
                String q1 = "Choose one image";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.conscientiousness.Question1 question = new com.example.ehsanullah.loginandregistration.conscientiousness.Question1(q1);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container, question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.opennessAnswersList.add(5, "0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;

                measuringAndStoringTheResultsInFirebaseFunc();

                Toast.makeText(getActivity(), "then i bet you will be a hero in any other field except painting...", Toast.LENGTH_SHORT).show();

                //for conscientiousness start
                Toast.makeText(getActivity(), "Lets talk about your living style in home.", Toast.LENGTH_SHORT).show();

//                String q1 = "do you like things in order?";
                String q1 = "Choose one image";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.conscientiousness.Question1 question = new com.example.ehsanullah.loginandregistration.conscientiousness.Question1(q1);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container, question);
                transaction.commit();
            }
        });

        return view;
    }

    private void measuringAndStoringTheResultsInFirebaseFunc() {

        //check how much answers are no and how much are yes
        int zeros = countNumberOfZerosOrOnes(Constant.opennessAnswersList, "0");
        int ones = countNumberOfZerosOrOnes(Constant.opennessAnswersList, "1");

        //measuring the nature of person
        measuringOpennessResults(zeros, ones);
    }


    private void measuringOpennessResults(int zeros, int ones) {

        String results = "";
        if (ones > zeros) {
            results = "sensitive to beauty, willing to try new things, creative, more likely to hold unconventional beliefs" +
                    " exploring new cultures but have no great interest in art or poetry.";

            sensitiveToBeauty = true;
            willingToTryNewThings = true;
            creative = true;
            exploringNewCulture = true;
            poetry = false;
            art = false;
        } else if (ones < zeros) {
            sensitiveToBeauty = false;
            willingToTryNewThings = false;
            creative = false;
            exploringNewCulture = true;
            poetry = true;
            art = true;
        } else if (ones == zeros) {

        }


        storeResultsInFirebaseFunc();
    }

    private void storeResultsInFirebaseFunc() {


        //________________________ Storing agreeableness results in Firebase _____________________________
        Firebase currentIdRef = Constant.FIREBASE_REF.child("questionnaireResults").child(Constant.CURRENT_USER_QUESTIONNAIRE_ID);

        currentIdRef.child("sensitiveToBeauty").setValue(sensitiveToBeauty);
        currentIdRef.child("willingToTryNewThings").setValue(willingToTryNewThings);
        currentIdRef.child("creative").setValue(creative);
        currentIdRef.child("exploringNewCulture").setValue(exploringNewCulture);
        currentIdRef.child("poetry").setValue(poetry);
        currentIdRef.child("art").setValue(art);
        currentIdRef.child("numOfAttemptedQuestions").setValue(Constant.NUM_OF_ATTEMPTED_QUESTIONS);


        //______________________________ Storing that how much questionnaire has been filled _____________________________
        Constant.FIREBASE_REF.child("questionnaireResults").child(Constant.CURRENT_USER_QUESTIONNAIRE_ID).child("filledQuestionnaireLevel")
                .setValue(new filledQuestionnaireLevel(true, false, false, false, false));
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





