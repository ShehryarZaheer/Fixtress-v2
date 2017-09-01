package com.example.ehsanullah.loginandregistration.extraversion;

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

public class Question8 extends Fragment {

    String q8;
    public Question8(String q8) {
        this.q8 = q8;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.extraversion_question_8,container,false);

        TextView tvQuestion8 = (TextView) view.findViewById(R.id.tvQuestion8);
        tvQuestion8.setText(q8);

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.extraversionAnswersList.add(7,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q9 = "choose one image.";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.extraversion.Question9 question = new com.example.ehsanullah.loginandregistration.extraversion.Question9(q9);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.extraversionAnswersList.add(7,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q9 = "choose one image.";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.extraversion.Question9 question = new com.example.ehsanullah.loginandregistration.extraversion.Question9(q9);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}
