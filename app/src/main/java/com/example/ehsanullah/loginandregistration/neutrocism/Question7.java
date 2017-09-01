package com.example.ehsanullah.loginandregistration.neutrocism;

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
 * Created by Ehsan Ullah on 4/1/2017.
 */

public class Question7 extends Fragment {

    String q7;
    public Question7(String q7) {
        this.q7 = q7;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.neutrocism_question_7,container,false);

        TextView tvQuestion7 = (TextView) view.findViewById(R.id.tvQuestion7);
        tvQuestion7.setText(q7);

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.neutrocismAnswersList.add(6,"0");//reversed
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"waoo it means i have a lucky friend.",Toast.LENGTH_SHORT).show();

                String q8 = "kindly tell me precisely are you";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.neutrocism.Question8 question = new com.example.ehsanullah.loginandregistration.neutrocism.Question8(q8);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.neutrocismAnswersList.add(6,"1");//reversed
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"oooh my poor friend you really need someone to talk then ... stick with me I promise I will do my best to make you happy and meke you feel relaxed \n" +
                        "\t\t\t\t\tmost of the time ... and that is why someone has created me actualy.",Toast.LENGTH_SHORT).show();

                String q8 = "kindly tell me precisely are you";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.neutrocism.Question8 question = new com.example.ehsanullah.loginandregistration.neutrocism.Question8(q8);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}
