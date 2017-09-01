package com.example.ehsanullah.loginandregistration.openness;

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
import com.example.ehsanullah.loginandregistration.R;

/**
 * Created by Ehsan Ullah on 3/31/2017.
 */

public class GetFavouriteReadings extends Fragment {

    String q;

    public GetFavouriteReadings(String q) {
        this.q = q;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.openness_get_favourite_readings, container, false);

        final TextView tvGetFavReadings = (TextView) view.findViewById(R.id.tvGetFavReadings);
        tvGetFavReadings.setText(q);

        final EditText etFavReadings = (EditText) view.findViewById(R.id.etFavReadings);

        ImageButton btnOk = (ImageButton) view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etFavReadings.getText().toString().equals("")) {

                    Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;

                    //                Toast.makeText(getActivity(), "great choice ... I will help you with your choice... by the way it is known that the person who loves reading and watching movies is interested in \n" +
//                        "\t\t\tthinking about something rather than practically doing it.. ", Toast.LENGTH_SHORT).show();

                    String q5 = "by the way it is known that the person who loves reading and watching movies is interested in \n" +
                            "\t\t\tthinking about something rather than practically doing it....according to this definition you are a practical man ... right?";

                    // Startinng an Fragment in question_container
                    com.example.ehsanullah.loginandregistration.openness.Question5 question = new com.example.ehsanullah.loginandregistration.openness.Question5(q5);
                    android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.question_container, question);
                    transaction.commit();


                    //___________________ storing the favourite readings value in firebase __________________
                    Constant.FIREBASE_REF.child("questionnaireResults").child(Constant.CURRENT_USER_QUESTIONNAIRE_ID)
                            .child("favReadings").setValue(etFavReadings.getText().toString());

                } else {
                    Toast.makeText(getActivity(), "Please enter the Movie name!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}
