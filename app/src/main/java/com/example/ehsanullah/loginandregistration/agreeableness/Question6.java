package com.example.ehsanullah.loginandregistration.agreeableness;

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
import com.example.ehsanullah.loginandregistration.MainActivity;
import com.example.ehsanullah.loginandregistration.R;

/**
 * Created by Ehsan Ullah on 3/29/2017.
 */

public class Question6 extends Fragment {

    String q6;
    public Question6(String q6) {
        this.q6 = q6;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agreeableness_question_6,container,false);

        TextView tvQuestion6 = (TextView) view.findViewById(R.id.tvQuestion6);
        tvQuestion6.setText(q6);

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Constant.agreeablenessAnswersList.add(5,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q4 = "(name) let say you are watching your favourit movie or doing your favourit activity and suddenly your neighbour ask you for some help ... what will you do then\n" +
                        "... will you go for their help?";

                Toast.makeText(getActivity(),"that so kind of you",Toast.LENGTH_SHORT).show();

                // Startinng an Fragment in question_container
                Question4 question = new Question4(q4);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.agreeablenessAnswersList.add(5,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"so mean (name) you should have help him.",Toast.LENGTH_SHORT).show();

                String q4 = "(name) let say you are watching your favourit movie or doing your favourit activity and suddenly your neighbour ask you for some help ... what will you do then\n" +
                        "... will you go for their help?";
                // Startinng an Fragment in question_container
                Question4 question = new Question4(q4);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}
