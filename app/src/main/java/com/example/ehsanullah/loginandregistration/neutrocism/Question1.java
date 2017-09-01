package com.example.ehsanullah.loginandregistration.neutrocism;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
        View view = inflater.inflate(R.layout.neutrocism_question_1,container,false);

        TextView tvQuestion1 = (TextView) view.findViewById(R.id.tvQuestion1);
        tvQuestion1.setText(q1);

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.neutrocismAnswersList.add(0,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q2 = "great its good to know ... i assume then you are not get stressed out easily ... right?";
                String dialogueAfterAnswerYes = "it will be really amazing to talk to you then.";
                String dialogueAfterAnswerNo = "it will be really amazing to talk to you then.";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.neutrocism.Question2 question = new com.example.ehsanullah.loginandregistration.neutrocism.Question2(q2,dialogueAfterAnswerYes,dialogueAfterAnswerNo);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.neutrocismAnswersList.add(0,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q2 = "ok I will keep it in mind and hopefully you will not irritate from me ... i assume then you are not get stressed out easily ... right?";
                String dialogueAfterAnswerYes = "it will be really amazing to talk to you.";
                String dialogueAfterAnswerNo = "it will be really amazing to talk to you.";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.neutrocism.Question2 question = new com.example.ehsanullah.loginandregistration.neutrocism.Question2(q2,dialogueAfterAnswerYes,dialogueAfterAnswerNo);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}

