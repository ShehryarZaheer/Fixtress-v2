package com.example.ehsanullah.loginandregistration.neutrocism;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.MainActivity;
import com.example.ehsanullah.loginandregistration.R;

/**
 * Created by Ehsan Ullah on 4/1/2017.
 */

public class Question5 extends Fragment {

    String q5;
    EditText etRateIt;

    public Question5(String q5) {
        this.q5 = q5;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.neutrocism_question_5, container, false);

        TextView tvQuestion5 = (TextView) view.findViewById(R.id.tvQuestion5);
        tvQuestion5.setText(q5);

        etRateIt = (EditText) view.findViewById(R.id.etRateIt);

        ImageButton btnOk = (ImageButton) view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rate = etRateIt.getText().toString();

                if (rate.contains("1") || rate.contains("2") || rate.contains("3") ||rate.contains("4") ||
                        rate.contains("one") || rate.contains("two") || rate.contains("three") || rate.contains("four"))
                {
                    Constant.neutrocismAnswersList.add(4,"1");
                    Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                    Toast.makeText(getActivity(), "hmmm... seems like you are a cool person .. thats a good thing in some perspective ... in this way you can give more attention to yourself \n" +
                            "\t\t\t\t\t\t\t... and improve your personality day by day",Toast.LENGTH_SHORT).show();

                    String q6 = "which one is alike you?";

                    // Startinng an Fragment in question_container
                    com.example.ehsanullah.loginandregistration.neutrocism.Question6 question = new com.example.ehsanullah.loginandregistration.neutrocism.Question6(q6);
                    android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.question_container, question);
                    transaction.commit();
                }

                else if(rate.contains("5") || rate.contains("6") || rate.contains("7") ||rate.contains("8") ||rate.contains("9") ||rate.contains("10") ||
                        rate.contains("five") || rate.contains("six") || rate.contains("seven") || rate.contains("eight") ||
                        rate.contains("nine") || rate.contains("ten"))
                {
                    Constant.neutrocismAnswersList.add(4,"0");
                    Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                    Toast.makeText(getActivity(), "hmmm... seems like you are a humble person who is worried about people around you ... thats a really sweet thing ... \n" +
                            "\t\t\t\t\t\t\tyou must be a favourite person for the people around you...",Toast.LENGTH_SHORT).show();

                    String q6 = "which one is alike you?";

                    // Startinng an Fragment in question_container
                    com.example.ehsanullah.loginandregistration.neutrocism.Question6 question = new com.example.ehsanullah.loginandregistration.neutrocism.Question6(q6);
                    android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.question_container, question);
                    transaction.commit();
                }
            }
        });

        return view;
    }
}

