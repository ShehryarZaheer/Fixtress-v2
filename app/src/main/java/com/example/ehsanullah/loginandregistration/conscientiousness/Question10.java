package com.example.ehsanullah.loginandregistration.conscientiousness;

import android.app.Fragment;
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
 * Created by Ehsan Ullah on 4/2/2017.
 */

public class Question10 extends Fragment {

    boolean disciplinedAndOrganized= false, dutiful= false, perfectionism = false;
    String q10;
    public Question10(String q10) {
        this.q10 = q10;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conscientiousness_question_10,container,false);

        TextView tvQuestion10 = (TextView) view.findViewById(R.id.tvQuestion10);
        tvQuestion10.setText(q10);

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.conscientiousnessAnswersList.add(9,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;

                measuringAndStoringTheResultsInFirebaseFunc();

                Toast.makeText(getActivity(),"thank you very much you gave me answer very gently now i know alot about you ... you are amaizing guy...",Toast.LENGTH_SHORT).show();

                //for extraversion start
                String q1 = "Let say you are attending any marriage and you are looking most beautiful among all the other people and they are looking and talking about you, you are like the hub of attention of all people\n" +
                        "\t... what do you feel like at that time ...";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.extraversion.Question1 question = new com.example.ehsanullah.loginandregistration.extraversion.Question1(q1);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.conscientiousnessAnswersList.add(9,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;

                measuringAndStoringTheResultsInFirebaseFunc();

                Toast.makeText(getActivity(),"thank you very much you gave me answer very gently now i know alot about you ... you are amaizing guy...",Toast.LENGTH_SHORT).show();

                //for extraversion start
                String q1 = "Let say you are attending any marriage and you are looking most beautiful among all the other people and they are looking and talking about you, you are like the hub of attention of all people\n" +
                        "\t... what do you feel like at that time ...";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.extraversion.Question1 question = new com.example.ehsanullah.loginandregistration.extraversion.Question1(q1);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }

    private void measuringAndStoringTheResultsInFirebaseFunc() {

        //check how much answers are no and how much are yes
        int zeros = countNumberOfZerosOrOnes(Constant.conscientiousnessAnswersList,"0");
        int ones = countNumberOfZerosOrOnes(Constant.conscientiousnessAnswersList,"1");

        //measuring the nature of person
        measuringConscientiousnessResults(zeros,ones);
    }


    private void measuringConscientiousnessResults(int zeros,int ones)
    {
        String results = "";
        if(ones > zeros)
        {
            results = "planned rather than spontaneous behavior, disciplined and organized,self-discipline, act dutifully, perfectionism";

            disciplinedAndOrganized = true;
            dutiful = true;
            perfectionism = true;
        }
        else if(ones < zeros)
        {
            results = "";

            disciplinedAndOrganized = false;
            dutiful = false;
            perfectionism = false;
        }
        else if(ones == zeros)
        {
            results = "";
        }


        storeResultsInFirebaseFunc();
    }

    private void storeResultsInFirebaseFunc() {

        //______________________________ Storing the consientiousness results on current user's pushed questionnaire_ID _____________________________
        Firebase currentIdRef = Constant.FIREBASE_REF.child("questionnaireResults").child(Constant.CURRENT_USER_QUESTIONNAIRE_ID);

        currentIdRef.child("disciplinedAndOrganized").setValue(disciplinedAndOrganized);
        currentIdRef.child("dutiful").setValue(dutiful);
        currentIdRef.child("perfectionism").setValue(perfectionism);
        currentIdRef.child("numOfAttemptedQuestions").setValue(Constant.NUM_OF_ATTEMPTED_QUESTIONS);


        //______________________________ Storing that how much questionnaire has been filled _____________________________
        currentIdRef.child("filledQuestionnaireLevel")
                .setValue(new filledQuestionnaireLevel(true,true,false,false,false));
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


