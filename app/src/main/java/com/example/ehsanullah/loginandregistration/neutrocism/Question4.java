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
import com.example.ehsanullah.loginandregistration.R;

/**
 * Created by Ehsan Ullah on 4/1/2017.
 */

public class Question4 extends Fragment {

    String q4;

    public Question4(String q4) {
        this.q4 = q4;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.neutrocism_question_4, container, false);

        TextView tvQuestion4 = (TextView) view.findViewById(R.id.tvQuestion4);
        tvQuestion4.setText(q4);

        final EditText etFavouritePet = (EditText) view.findViewById(R.id.etFavouritePet);

        ImageButton btnOk = (ImageButton) view.findViewById(R.id.btnOk);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etFavouritePet.getText().toString().equals("")) {

                    Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;

                    String q5 = "nice choice ... Lets suppose you are facing three problems at a time" +
                            "a) Number one is, someone poisend your (pet name)\n" +
                            "\t\tb) Number two is, somone rob your neighbours\n" +
                            "\t\tc) and number three is, your friend is in serious trouble due to money shotage.\n" +
                            "\t\t\n" +
                            "\t\tall three problems occurs at the same time ... now i want you to rate from 1 to 10 ... how much you will be worried?";

                    // Startinng an Fragment in question_container
                    com.example.ehsanullah.loginandregistration.neutrocism.Question5 question = new com.example.ehsanullah.loginandregistration.neutrocism.Question5(q5);
                    android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.question_container, question);
                    transaction.commit();


                    //___________________ storing the favPet value in firebase _____________________________
                    Constant.FIREBASE_REF.child("questionnaireResults").child(Constant.CURRENT_USER_QUESTIONNAIRE_ID)
                            .child("favPet").setValue(etFavouritePet.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "Please enter the Pet name!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}

