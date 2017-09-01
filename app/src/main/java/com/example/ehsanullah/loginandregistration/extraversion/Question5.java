package com.example.ehsanullah.loginandregistration.extraversion;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.MainActivity;
import com.example.ehsanullah.loginandregistration.R;

/**
 * Created by Ehsan Ullah on 4/1/2017.
 */

public class Question5 extends Fragment {

    String q5;
    public Question5(String q5) {
        this.q5 = q5;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.extraversion_question_5,container,false);

        TextView tvQuestion5 = (TextView) view.findViewById(R.id.tvQuestion5);
        tvQuestion5.setText(q5);

        ImageView ivLeftImage = (ImageView) view.findViewById(R.id.ivLeftImage);
        ivLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.extraversionAnswersList.add(4,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q6 = "(Name) do you think alot before doing anything or before speak anything?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.extraversion.Question6 question = new com.example.ehsanullah.loginandregistration.extraversion.Question6(q6);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageView ivRightImage = (ImageView) view.findViewById(R.id.ivRightImage);
        ivRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.extraversionAnswersList.add(4,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q6 = "(Name) do you think alot before doing anything or before speak anything?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.extraversion.Question6 question = new com.example.ehsanullah.loginandregistration.extraversion.Question6(q6);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}




