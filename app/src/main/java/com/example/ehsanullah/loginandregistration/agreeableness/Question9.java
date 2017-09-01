package com.example.ehsanullah.loginandregistration.agreeableness;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.questionnnaire_results.filledQuestionnaireLevel;
import com.firebase.client.Firebase;

import java.util.ArrayList;

/**
 * Created by Ehsan Ullah on 4/14/2017.
 */

public class Question9 extends Fragment {

    boolean kind= false,trustworthy= false, trustingOthers= false, friendly = false;
    String q9;

    public Question9(String q9) {
        this.q9 = q9;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agreeableness_question_9,container,false);

        TextView tvQuestion9 = (TextView) view.findViewById(R.id.tvQuestion9);
        tvQuestion9.setText(q9);

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.agreeablenessAnswersList.add(8,"0");//reversed
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;

                measuringAndStoringTheResultsInFirebaseFunc();

                //for neutrocism start
                String q1 = "(Name)I don't want you get bored or irritated while communicating with me thats why i wanna know do you get irritated easily?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.neutrocism.Question1 question = new com.example.ehsanullah.loginandregistration.neutrocism.Question1(q1);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.agreeablenessAnswersList.add(8,"1");//reversed
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;

                measuringAndStoringTheResultsInFirebaseFunc();

                //for neutrocism start
                String q1 = "(Name)I don't want you get bored or irritated while communicating with me thats why i wanna know do you get irritated easily?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.neutrocism.Question1 question = new com.example.ehsanullah.loginandregistration.neutrocism.Question1(q1);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });


        return view;
    }

    private void measuringAndStoringTheResultsInFirebaseFunc() {

        //check how much answers are no and how much are yes
        int zeros = countNumberOfZerosOrOnes(Constant.agreeablenessAnswersList,"0");
        int ones = countNumberOfZerosOrOnes(Constant.agreeablenessAnswersList,"1");

        //measuring the nature of person
        measuringagreeablenessResults(zeros,ones);
    }


    private void measuringagreeablenessResults(int zeros,int ones)
    {
        String results = "";
        if(ones > zeros)
        {
            results = "kind, trusting and trustworthy, helpful, and willing to compromise their interests with others";

            kind = true;
            trustworthy = true;
            trustingOthers = true;
            friendly = true;

        }
        else if(ones < zeros)
        {
            results = "place self-interest above getting along with others, unconcerned with others' well-being," +
                    "unfriendly, and uncooperative";

            kind = false;
            trustworthy = false;
            trustingOthers = false;
            friendly = false;
        }
        else if(ones == zeros)
        {
            results = "";
        }


        storeResultsInFirebaseFunc();
    }

    private void storeResultsInFirebaseFunc() {

        //______________________________ Storing the agreeableness results on current user's pushed questionnaire_ID _____________________________
        Firebase currentIdRef = Constant.FIREBASE_REF.child("questionnaireResults").child(Constant.CURRENT_USER_QUESTIONNAIRE_ID);

        currentIdRef.child("kind").setValue(kind);
        currentIdRef.child("trustworthy").setValue(trustworthy);
        currentIdRef.child("trustingOthers").setValue(trustingOthers);
        currentIdRef.child("friendly").setValue(friendly);
        currentIdRef.child("numOfAttemptedQuestions").setValue(Constant.NUM_OF_ATTEMPTED_QUESTIONS);


        //______________________________ Storing that how much questionnaire has been filled _____________________________
        currentIdRef.child("filledQuestionnaireLevel")
                .setValue(new filledQuestionnaireLevel(true,true,true,true,false));
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

