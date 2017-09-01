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

public class Question7 extends Fragment {

    String q7;
    public Question7(String q7) {
        this.q7 = q7;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.extraversion_question_7,container,false);

        TextView tvQuestion7 = (TextView) view.findViewById(R.id.tvQuestion7);
        tvQuestion7.setText(q7);


        ImageView ivLeftImage = (ImageView) view.findViewById(R.id.ivLeftImage);
        ivLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.extraversionAnswersList.add(6,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q8 = "do you like it?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.extraversion.Question8 question = new com.example.ehsanullah.loginandregistration.extraversion.Question8(q8);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageView ivRightImage = (ImageView) view.findViewById(R.id.ivRightImage);
        ivRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.extraversionAnswersList.add(6,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q8 = "do you like it?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.extraversion.Question8 question = new com.example.ehsanullah.loginandregistration.extraversion.Question8(q8);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}

