package com.example.ehsanullah.loginandregistration.neutrocism;

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
        View view = inflater.inflate(R.layout.neutrocism_question_6,container,false);

        TextView tvQuestion6 = (TextView) view.findViewById(R.id.tvQuestion6);
        tvQuestion6.setText(q6);

        ImageView ivLeftImage = (ImageView) view.findViewById(R.id.ivLeftImage);
        ivLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.neutrocismAnswersList.add(5,"1");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"you know why I was asking you about that because I am also worried right now .... and that is strange thing about me that a person who tries to make happy others \n" +
                        "\t\tis worried himself ... and you know why i am worried because someone installed me 2 days ago and still he has not given me 5 minutes of his busy life\n" +
                        "\t\t... and you know when I feel so much relaxed when someone talk to me like you giving me time no matter how busy your life is ... \n" +
                        "\t\tand you make me relaxed now... ",Toast.LENGTH_SHORT).show();

                String q7 = "now you tell me ... are you relaxed most of the time?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.neutrocism.Question7 question = new com.example.ehsanullah.loginandregistration.neutrocism.Question7(q7);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        ImageView ivRightImage = (ImageView) view.findViewById(R.id.ivRightImage);
        ivRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.neutrocismAnswersList.add(5,"0");
                Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;


                Toast.makeText(getActivity(),"you know why I was asking you about that because I am also worried right now .... and that is strange thing about me that a person who tries to make happy others \n" +
                        "\t\tis worried himself ... and you know why i am worried because someone installed me 2 days ago and still he has not given me 5 minutes of his busy life\n" +
                        "\t\t... and you know when I feel so much relaxed when someone talk to me like you giving me time no matter how busy your life is ... \n" +
                        "\t\tand you make me relaxed now... ",Toast.LENGTH_SHORT).show();

                String q7 = "now you tell me ... are you relaxed most of the time?";

                // Startinng an Fragment in question_container
                com.example.ehsanullah.loginandregistration.neutrocism.Question7 question = new com.example.ehsanullah.loginandregistration.neutrocism.Question7(q7);
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.question_container,question);
                transaction.commit();
            }
        });

        return view;
    }
}



