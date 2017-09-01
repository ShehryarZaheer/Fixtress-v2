package com.example.ehsanullah.loginandregistration.extraversion;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.agreeableness.Question2;
import com.example.ehsanullah.loginandregistration.questionnnaire_results.filledQuestionnaireLevel;
import com.firebase.client.Firebase;

import java.util.ArrayList;

/**
 * Created by Ehsan Ullah on 4/1/2017.
 */

public class Question9 extends Fragment {

    boolean attentionSeeking= false, social= false, feelingEnergeticAroundOthers= false;
    String q9;
    public Question9(String q9) {
        this.q9 = q9;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.extraversion_question_9,container,false);

        TextView tvQuestion9 = (TextView) view.findViewById(R.id.tvQuestion9);
        tvQuestion9.setText(q9);

        ImageView ivLeftImage = (ImageView) view.findViewById(R.id.ivLeftImage);
        ivLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.extraversionAnswersList.add(8,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;

                measuringAndStoringTheResultsInFirebaseFunc();

                Toast.makeText(getActivity(),"You have clicked no Button.", Toast.LENGTH_SHORT).show();

                //for agreeableness start
                String q1 = "(name) did it ever happened that any of your friend or any other person you know " +
                        "told you about his worry and after listening him you got worried too?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.agreeableness.Question2 question = new Question2(q1);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageView ivRightImage = (ImageView) view.findViewById(R.id.ivRightImage);
        ivRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.extraversionAnswersList.add(8,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;

                measuringAndStoringTheResultsInFirebaseFunc();

                Toast.makeText(getActivity(),"You have clicked no Button.", Toast.LENGTH_SHORT).show();

                //for agreeableness start
                String q1 = "(name) did it ever happened that any of your friend or any other person you know " +
                        "told you about his worry and after listening him you got worried too?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.agreeableness.Question2 question = new com.example.ehsanullah.loginandregistration.agreeableness.Question2(q1);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });


        return view;
    }

    private void measuringAndStoringTheResultsInFirebaseFunc() {

        //check how much answers are no and how much are yes
        int zeros = countNumberOfZerosOrOnes(Constant.extraversionAnswersList,"0");
        int ones = countNumberOfZerosOrOnes(Constant.extraversionAnswersList,"1");

        //measuring the nature of person
        measuringExtraversionResults(zeros,ones);
    }


    private void measuringExtraversionResults(int zeros,int ones)
    {
        String results = "";
        if(ones > zeros)
        {
            results = "attention seeking, draw their energy from being around others, so they tend to be more sociable (not to be confused with outgoing!)";

            attentionSeeking = true;
            social = true;
            feelingEnergeticAroundOthers = true;
        }
        else if(ones < zeros)
        {
            results = "";

            attentionSeeking = false;
            social = false;
            feelingEnergeticAroundOthers = false;
        }
        else if(ones == zeros)
        {
            results = "";
        }


        storeResultsInFirebaseFunc();
    }

    private void storeResultsInFirebaseFunc() {

        //______________________________ Storing the extraversion results on current user's pushed questionnaire_ID _____________________________
        Firebase currentIdRef = Constant.FIREBASE_REF.child("questionnaireResults").child(Constant.CURRENT_USER_QUESTIONNAIRE_ID);

        currentIdRef.child("attentionSeeking").setValue(attentionSeeking);
        currentIdRef.child("social").setValue(social);
        currentIdRef.child("feelingEnergeticAroundOthers").setValue(feelingEnergeticAroundOthers);
        currentIdRef.child("numOfAttemptedQuestions").setValue(Constant.NUM_OF_ATTEMPTED_QUESTIONS);


        //______________________________ Storing that how much questionnaire has been filled _____________________________
        currentIdRef.child("filledQuestionnaireLevel")
                .setValue(new filledQuestionnaireLevel(true,true,true,false,false));
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
