package com.example.ehsanullah.loginandregistration.conscientiousness;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.MainActivity;
import com.example.ehsanullah.loginandregistration.R;

/**
 * Created by Ehsan Ullah on 4/2/2017.
 */

public class Question5 extends Fragment {

    String q5;
    public Question5(String q5) {
        this.q5 = q5;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conscientiousness_question_5,container,false);

        TextView tvQuestion5 = (TextView) view.findViewById(R.id.tvQuestion5);
        tvQuestion5.setText(q5);

        ImageView ivLeftImage = (ImageView) view.findViewById(R.id.ivLeftImage);
        ivLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.conscientiousnessAnswersList.add(4,"0");//reversed
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(), "hmmm ... descent life style i impressed.",Toast.LENGTH_SHORT).show();

                String q6 = "lets suppose I gave you a problem and its solution is specified in two lines but if you want that your solution should have efficiency and there should be no error then you have to read the details of 5 lines then would you like to read details?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.conscientiousness.Question6 question = new com.example.ehsanullah.loginandregistration.conscientiousness.Question6(q6);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageView ivRightImage = (ImageView) view.findViewById(R.id.ivRightImage);
        ivRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.conscientiousnessAnswersList.add(4,"1");//reversed
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(), "most of the people live this way ... i think they dont wanna waste time on this.",Toast.LENGTH_SHORT).show();

                String q6 = "lets suppose I gave you a problem and its solution is specified in two lines but if you want that your solution should have efficiency and there should \n" +
                        "\t\tbe no error then you have to read the details of 5 lines then would you like to read details?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.conscientiousness.Question6 question = new com.example.ehsanullah.loginandregistration.conscientiousness.Question6(q6);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}

