package com.example.ehsanullah.loginandregistration.openness;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.questionnnaire_results.filledQuestionnaireLevel;
import com.example.ehsanullah.loginandregistration.questionnnaire_results.questionnaireResults;
import com.firebase.client.Firebase;

/**
 * Created by Ehsan Ullah on 3/31/2017.
 */

public class GetFavouriteMovies extends Fragment {

    String q;

    public GetFavouriteMovies(String q) {
        this.q = q;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.openness_get_favourite_movies, container, false);

        TextView tvGetFavMovies = (TextView) view.findViewById(R.id.tvGetFavMovies);
        tvGetFavMovies.setText(q);

        final EditText etFavMovies = (EditText) view.findViewById(R.id.etFavMovies);

        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!etFavMovies.getText().toString().equals("")) {

                    Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;

                    Toast.makeText(getActivity(), "great choice ... I will help you with your choice... by the way it is known that the person who loves reading and watching movies is interested in \n" +
                            "\t\t\tthinking about something rather than practically doing it.. ", Toast.LENGTH_SHORT).show();

                    String q5 = "according to this definition you are not a practical man you are more interested in thinking and innovation of ideas... right?";

                    // Startinng an Fragment in question_container
                    com.example.ehsanullah.loginandregistration.openness.Question5 question = new com.example.ehsanullah.loginandregistration.openness.Question5(q5);
                    android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.question_container, question);
                    transaction.commit();


                    //_____________________ storing data in firebase or creating node for questionnaire_data, according to condition _________
                    if (Constant.CURRENT_USER_QUESTIONNAIRE_ID.equals("")) {
                        //______________________________ getting the pushed questionnaire_ID _____________________________
                        Constant.CURRENT_USER_QUESTIONNAIRE_ID = Constant.FIREBASE_REF.child("questionnaireResults").push().getKey().toString();

                        //______________________________ Storing the openness results on current user's pushed questionnaire_ID _____________________________
                        Constant.FIREBASE_REF.child("questionnaireResults").child(Constant.CURRENT_USER_QUESTIONNAIRE_ID)
                                .setValue(new questionnaireResults(
                                        Constant.CURRENT_USERS_EMAIL, etFavMovies.getText().toString(),
                                        new filledQuestionnaireLevel(false,false,false,false,false)));
                    } else {
                        Firebase currentIdRef = Constant.FIREBASE_REF.child("questionnaireResults").child(Constant.CURRENT_USER_QUESTIONNAIRE_ID);

                        currentIdRef.child("email").setValue(Constant.CURRENT_USERS_EMAIL);
                        currentIdRef.child("favMovies").setValue(etFavMovies.getText().toString());
                        currentIdRef.child("filledQuestionnaireLevel").setValue(new filledQuestionnaireLevel(false,false,false,false,false));
                    }

                } else {
                    Toast.makeText(getActivity(), "Please enter the Movie name!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}




