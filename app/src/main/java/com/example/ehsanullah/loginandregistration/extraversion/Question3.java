package com.example.ehsanullah.loginandregistration.extraversion;

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
import com.example.ehsanullah.loginandregistration.R;

/**
 * Created by Ehsan Ullah on 4/1/2017.
 */

public class Question3 extends Fragment {

    String q3;
    public Question3(String q3) {
        this.q3 = q3;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.extraversion_question_3,container,false);

        TextView tvQuestion3 = (TextView) view.findViewById(R.id.tvQuestion3);
        tvQuestion3.setText(q3);

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.extraversionAnswersList.add(2,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"cool ... you must have many friends then.",Toast.LENGTH_SHORT).show();

                // and Answer of Question4 will be no
                //thats why we move to Question5 directly

                String q5 = "choose which envoironment you like the most";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.extraversion.Question5 question = new com.example.ehsanullah.loginandregistration.extraversion.Question5(q5);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.extraversionAnswersList.add(2,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q4 = "seems like you dont talk much huh?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.extraversion.Question4 question = new com.example.ehsanullah.loginandregistration.extraversion.Question4(q4);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}


