package com.example.ehsanullah.loginandregistration.openness;

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
 * Created by Ehsan Ullah on 3/30/2017.
 */

public class Question4 extends Fragment {

    String q4;
    public Question4(String q4) {
        this.q4 = q4;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.openness_question_4,container,false);

        TextView tvQuestion4 = (TextView) view.findViewById(R.id.tvQuestion4);
        tvQuestion4.setText(q4);

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.opennessAnswersList.add(3,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q = "great ... tell me which type of movies you like to see ... like comedy, action, thriller, horror or any other type that you like?.";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.openness.GetFavouriteMovies question = new com.example.ehsanullah.loginandregistration.openness.GetFavouriteMovies(q);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.opennessAnswersList.add(3,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                String q4_a = "ooh really?? i thought you would say ooh yeah I love to see movies of this and that but you are not .... its ok ... let me use my 6th sense here \n" +
                        "you like to reading books or articles or something ... right?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.openness.Question4_a question = new com.example.ehsanullah.loginandregistration.openness.Question4_a(q4_a);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}


