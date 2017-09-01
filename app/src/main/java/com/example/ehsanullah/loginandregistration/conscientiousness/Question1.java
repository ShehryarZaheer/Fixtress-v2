package com.example.ehsanullah.loginandregistration.conscientiousness;

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
 * Created by Ehsan Ullah on 4/2/2017.
 */

public class Question1 extends Fragment {

    String q1;
    public Question1(String q1) {
        this.q1 = q1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conscientiousness_question_1,container,false);

        TextView tvQuestion1 = (TextView) view.findViewById(R.id.tvQuestion1);
        tvQuestion1.setText(q1);

        ImageView ivLeftImage = (ImageView) view.findViewById(R.id.ivLeftImage);
        ivLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.conscientiousnessAnswersList.add(0,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q2 = "Did you perform all of your duties right away?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.conscientiousness.Question2 question = new com.example.ehsanullah.loginandregistration.conscientiousness.Question2(q2);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageView ivRightImage = (ImageView) view.findViewById(R.id.ivRightImage);
        ivRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.conscientiousnessAnswersList.add(0,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q2 = "Did you perform all of your duties right away?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.conscientiousness.Question2 question = new com.example.ehsanullah.loginandregistration.conscientiousness.Question2(q2);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}





