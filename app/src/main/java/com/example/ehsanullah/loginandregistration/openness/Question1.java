package com.example.ehsanullah.loginandregistration.openness;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.MainActivity;
import com.example.ehsanullah.loginandregistration.R;

/**
 * Created by Ehsan Ullah on 3/30/2017.
 */

public class Question1 extends Fragment implements View.OnKeyListener{

    String q1;
    public Question1(String q1) {
        this.q1 = q1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.openness_question_1,container,false);

        TextView tvQuestion = (TextView) view.findViewById(R.id.tvQuestion1);
        tvQuestion.setText(q1);

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.opennessAnswersList.add(0,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"ooh congrats i bet you will be rich soon and have a great life ahead.",Toast.LENGTH_SHORT).show();

                String q2 = "(Name) I observe that you are are very smart, pick up and understand things very quickly... am i right about you?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.openness.Question2 question = new com.example.ehsanullah.loginandregistration.openness.Question2(q2);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.opennessAnswersList.add(0,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"ok then its not a big deal you must be damn good in something else ... are not you?",Toast.LENGTH_SHORT).show();

                String q2 = "(Name) I observe that you are are very smart, pick up and understand things very quickly... am i right about you?";
                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.openness.Question2 question = new com.example.ehsanullah.loginandregistration.openness.Question2(q2);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });


        return view;
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {


        return false;
    }
}

