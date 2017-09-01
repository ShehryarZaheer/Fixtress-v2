package com.example.ehsanullah.loginandregistration.agreeableness;

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
 * Created by Ehsan Ullah on 3/29/2017.
 */

public class Question2 extends Fragment {

    String q2;
    public Question2(String q2) {
        this.q2 = q2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agreeableness_question_2,container,false);

        TextView tvQuestion2 = (TextView) view.findViewById(R.id.tvQuestion2);
        tvQuestion2.setText(q2);

        //Answer of Question1 will same as the answer of the Question8 of Extraversion
        Constant.agreeablenessAnswersList.add( 0, Constant.extraversionAnswersList.get(7).toString() );

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.agreeablenessAnswersList.add(1,"1");
                Constant.agreeablenessAnswersList.add(2,"1");//same results for Question3
                Constant.agreeablenessAnswersList.add(4,"1");//same results for Question5
                Constant.agreeablenessAnswersList.add(6,"0");//same results for Question7(reversed)
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q6 = "then did you help him with his worry to put him on ease?";

                // Startinng an Fragment in question_container
                Question6 question = new Question6(q6);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.agreeablenessAnswersList.add(1,"0");
                Constant.agreeablenessAnswersList.add(2,"0");//same results for Question3
                Constant.agreeablenessAnswersList.add(4,"0");//same results for Question5
                Constant.agreeablenessAnswersList.add(6,"1");//same results for Question7(reversed)
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q6 = "then did you help him with his worry to put him on ease?";
                // Startinng an Fragment in question_container
                Question6 question = new Question6(q6);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}
