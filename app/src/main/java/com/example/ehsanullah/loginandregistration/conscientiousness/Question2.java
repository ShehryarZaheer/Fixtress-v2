package com.example.ehsanullah.loginandregistration.conscientiousness;

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
 * Created by Ehsan Ullah on 4/2/2017.
 */

public class Question2 extends Fragment {

    String q2;

    public Question2(String q2) {
        this.q2 = q2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conscientiousness_question_2,container,false);

        TextView tvQuestion2 = (TextView) view.findViewById(R.id.tvQuestion2);
        tvQuestion2.setText(q2);

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.conscientiousnessAnswersList.add(1,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(), "oookkk ... (name)is an responsible person huh?",Toast.LENGTH_SHORT).show();

                String q3 = "do you follow a scedule?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.conscientiousness.Question3 question = new com.example.ehsanullah.loginandregistration.conscientiousness.Question3(q3);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.conscientiousnessAnswersList.add(1,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"you must have made your parents to punish you for that ... hahaha",Toast.LENGTH_SHORT).show();

                String q3 = "do you follow a scedule?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.conscientiousness.Question3 question = new com.example.ehsanullah.loginandregistration.conscientiousness.Question3(q3);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}


