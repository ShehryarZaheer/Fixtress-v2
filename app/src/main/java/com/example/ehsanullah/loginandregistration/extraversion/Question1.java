package com.example.ehsanullah.loginandregistration.extraversion;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.MainActivity;
import com.example.ehsanullah.loginandregistration.R;

/**
 * Created by Ehsan Ullah on 4/1/2017.
 */

public class Question1 extends Fragment {

    String q1;
    public Question1(String q1) {
        this.q1 = q1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.extraversion_question_1,container,false);

        TextView tvQuestion1 = (TextView) view.findViewById(R.id.tvQuestion1);
        tvQuestion1.setText(q1);

        RadioButton rbAwkward = (RadioButton) view.findViewById(R.id.rbAwkward);
        rbAwkward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.extraversionAnswersList.add(0,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q2 = "ooh it means you dont feel comfortable if many people around you ... right?";
                String dialogueAfterAnswerYes = "yeah sometimes it feels really awkward in this situation.";
                String dialogueAfterAnswerNo = "good that make you more confident.";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.extraversion.Question2 question = new com.example.ehsanullah.loginandregistration.extraversion.Question2(q2,dialogueAfterAnswerYes,dialogueAfterAnswerNo);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        RadioButton rbHappy = (RadioButton) view.findViewById(R.id.rbHappy);
        rbHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.extraversionAnswersList.add(0,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q2 = "ok it means you feel comfortable if many people around you... right?";
                String dialogueAfterAnswerYes = "ok you are unique type of person who want to stay alone.";
                String dialogueAfterAnswerNo = "its really nice to talk people around you it makes you feel relaxed and keep you calm and happy.";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.extraversion.Question2 question = new com.example.ehsanullah.loginandregistration.extraversion.Question2(q2,dialogueAfterAnswerYes,dialogueAfterAnswerNo);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}


