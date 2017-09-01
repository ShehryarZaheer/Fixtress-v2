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

public class Question4_a extends Fragment {

    String q4_a;
    public Question4_a(String q4_a) {
        this.q4_a = q4_a;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.openness_question_4_a,container,false);

        TextView tvQuestion4_a = (TextView) view.findViewById(R.id.tvQuestion4_a);
        tvQuestion4_a.setText(q4_a);

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.opennessAnswersList.add(6,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                //Toast.makeText(getActivity(),"you are a straight guy",Toast.LENGTH_SHORT).show();

                String q = "ooh my goodness how intelligent I am ... hahaha ... let me be a detective (in detective voice) ... whats you like to read ... politics, sports,\n" +
                        "\t\t\t\t\tnews papers, novels tell me what you like?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.openness.GetFavouriteReadings question = new com.example.ehsanullah.loginandregistration.openness.GetFavouriteReadings(q);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.opennessAnswersList.add(6,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                //Toast.makeText(getActivity(),"thats exactly what one should do ... nice nature",Toast.LENGTH_SHORT).show();

                String q5 = "hands up boss ... I am unable to judge you ... by the way it is known that the person who loves reading and watching movies is interested in \n" +
                        "\t\t\tthinking about something rather than practically doing it... according to this definition you are a practical man ... right?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.openness.Question5 question = new com.example.ehsanullah.loginandregistration.openness.Question5(q5);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}



