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
 * Created by Ehsan Ullah on 7/25/2017.
 */

public class getFavouriteSinger extends Fragment {

    String favSinger;

    public getFavouriteSinger(String favSinger) {
        this.favSinger = favSinger;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.openness_get_favourite_singer, container, false);

        TextView tvGetFavPainter = (TextView) view.findViewById(R.id.tvGetFavSinger);
        tvGetFavPainter.setText(favSinger);

        final EditText etFavSinger = (EditText) view.findViewById(R.id.etFavSinger);

        ImageButton btnOk = (ImageButton) view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!etFavSinger.getText().toString().equals("")) {


                    Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;
                    Toast.makeText(getActivity(), "Show some pics in web View", Toast.LENGTH_SHORT).show();

                    String q6 = "now tell me something about your imaginations do you think you have good imaginations?";

                    // Temporary fragment
                    // Startinng an Fragment in question_container
                    com.example.ehsanullah.loginandregistration.openness.Question6 question = new com.example.ehsanullah.loginandregistration.openness.Question6(q6);
                    android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.question_container, question);
                    transaction.commit();


                    //___________________ storing the favourite singer value in firebase
                    Constant.FIREBASE_REF.child("questionnaireResults").child(Constant.CURRENT_USER_QUESTIONNAIRE_ID)
                            .child("favSinger").setValue(etFavSinger.getText().toString());

//                Constant.opennessAnswersList.add(7,"1");


                } else {
                    Toast.makeText(getActivity(), "Please enter the Movie name!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }
}
