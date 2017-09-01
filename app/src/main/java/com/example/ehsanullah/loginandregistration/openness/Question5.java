package com.example.ehsanullah.loginandregistration.openness;

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
 * Created by Ehsan Ullah on 3/30/2017.
 */

public class Question5 extends Fragment {

    String q5;
    public Question5(String q5) {
        this.q5 = q5;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.openness_question_5,container,false);

        TextView tvQuestion5 = (TextView) view.findViewById(R.id.tvQuestion5);
        tvQuestion5.setText(q5);

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.opennessAnswersList.add(4,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"ok ... many people says that overthinking is not a good habit although it has many disadvantages but here are some amazing advantages\n" +
                        "\t\t\t\t\talso like painters are the best example of that ... if you are overthinker then you can be hell of a painter because they have very strong imagination power \n" +
                        "\t\t\t\t\tlike \"Leonardo da Vinci\" is the best painter of the history and its imaginations was beyond the world ... ",Toast.LENGTH_SHORT).show();

                String q5_a = "Who is your favourite painter?";

                // Startinng an Fragment in question_container
                getFavouritePainter question = new getFavouritePainter(q5_a);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageButton btnNo = (ImageButton) view.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.opennessAnswersList.add(4,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"ok ... many people says that overthinking is not a good habit although it has many disadvantages but here are some amazing advantages\n" +
                        "\t\t\t\t\talso like painters are the best example of that ... if you are overthinker then you can be hell of a painter because they have very strong imagination power \n" +
                        "\t\t\t\t\tlike \"Leonardo da Vinci\" is the best painter of the history and its imaginations was beyond the world ... ",Toast.LENGTH_SHORT).show();

                String q5_a = "Who is your favourite painter?";

                // Startinng an Fragment in question_container
                getFavouritePainter question = new getFavouritePainter(q5_a);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}



