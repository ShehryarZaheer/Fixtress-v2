package com.example.ehsanullah.loginandregistration.agreeableness;

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
 * Created by Ehsan Ullah on 3/29/2017.
 */

public class Question4 extends Fragment {

    String q4;
    public Question4(String q4) {
        this.q4 = q4;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agreeableness_question_4,container,false);

        TextView tvQuestion4 = (TextView) view.findViewById(R.id.tvQuestion4);
        tvQuestion4.setText(q4);

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.agreeablenessAnswersList.add(3,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q8 = "(reversed)(name) suppose you know a guy and you dislike him ... will you hasitate to insult him at any point?";

                Toast.makeText(getActivity(),"so you have a soft heart",Toast.LENGTH_SHORT).show();

                // Startinng an Fragment in question_container
                Question8 question = new Question8(q8);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.agreeablenessAnswersList.add(3,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"you are so serious about your hobbies ... good",Toast.LENGTH_SHORT).show();

                String q8 = "(reversed)(name) suppose you know a guy and you dislike him ... will you hasitate to insult him at any point?";

                // Startinng an Fragment in question_container
                Question8 question = new Question8(q8);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}
