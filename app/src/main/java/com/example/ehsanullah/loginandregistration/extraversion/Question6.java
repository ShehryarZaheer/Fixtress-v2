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
import com.example.ehsanullah.loginandregistration.MainActivity;
import com.example.ehsanullah.loginandregistration.R;

/**
 * Created by Ehsan Ullah on 4/1/2017.
 */

public class Question6 extends Fragment {

    String q6;
    public Question6(String q6) {
        this.q6 = q6;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.extraversion_question_6,container,false);

        TextView tvQuestion6 = (TextView) view.findViewById(R.id.tvQuestion6);
        tvQuestion6.setText(q6);

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.extraversionAnswersList.add(5,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"well i dont think anything before doing infact I never think because I am programmed... we are totaly opposite in this manner.",Toast.LENGTH_SHORT).show();

                String q7 = "Choose which type of person you are?";

                // Startinng an Fragment in question_container
                    com.example.ehsanullah.loginandregistration.extraversion.Question7 question = new com.example.ehsanullah.loginandregistration.extraversion.Question7(q7);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.extraversionAnswersList.add(5,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"ooh waoo we are alike in this manner because me too cant think before doing anything.. infact i cant think because I am programmed.",Toast.LENGTH_SHORT).show();

                String q7 = "Choose which type of person you are?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.extraversion.Question7 question = new com.example.ehsanullah.loginandregistration.extraversion.Question7(q7);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}
