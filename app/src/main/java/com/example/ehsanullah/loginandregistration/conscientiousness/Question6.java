package com.example.ehsanullah.loginandregistration.conscientiousness;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.MainActivity;
import com.example.ehsanullah.loginandregistration.R;

/**
 * Created by Ehsan Ullah on 4/2/2017.
 */

public class Question6 extends Fragment {

    String q6;
    public Question6(String q6) {
        this.q6 = q6;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conscientiousness_question_6,container,false);

        TextView tvQuestion6 = (TextView) view.findViewById(R.id.tvQuestion6);
        tvQuestion6.setText(q6);

        Button btnYes = (Button) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.conscientiousnessAnswersList.add(5,"1");
                Constant.conscientiousnessAnswersList.add(6,"1");//same results for question7
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"ooh you are just like me because i am also very precise about everything and paying attention to details of anything.",Toast.LENGTH_SHORT).show();

                // "are you exacting in your work?" is question7 and we have to draw its result from same question6
                //thats why we will move direct towards question8

                String q8 = "You often forget to put things back in their proper place?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.conscientiousness.Question8 question = new com.example.ehsanullah.loginandregistration.conscientiousness.Question8(q8);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        Button btnNo = (Button) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.conscientiousnessAnswersList.add(5,"0");
                Constant.conscientiousnessAnswersList.add(6,"0");//same results for question7
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"ok you like to work fast ... its a good things in this way you can run with this fast world.",Toast.LENGTH_SHORT).show();

                // "are you exacting in your work?" is question7 and we have to draw its result from same question6
                //thats why we will move direct towards question8

                String q8 = "You often forget to put things back in their proper place?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.conscientiousness.Question8 question = new com.example.ehsanullah.loginandregistration.conscientiousness.Question8(q8);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}

