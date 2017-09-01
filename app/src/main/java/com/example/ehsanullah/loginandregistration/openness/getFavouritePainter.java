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

public class getFavouritePainter extends Fragment {

    String favPainter;

    public getFavouritePainter(String favPainter) {
        this.favPainter = favPainter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.openness_get_favourite_painter, container, false);

        final TextView tvGetFavPainter = (TextView) view.findViewById(R.id.tvGetFavPainter);
        tvGetFavPainter.setText(favPainter);


        final EditText etFavPainter = (EditText) view.findViewById(R.id.etFavPainter);

        ImageButton btnYes = (ImageButton) view.findViewById(R.id.btnOk);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!etFavPainter.getText().toString().equals("")) {


                    Constant.NUM_OF_ATTEMPTED_QUESTIONS +=1;

                    String favSinger = "Who is your favourite singer?";

                    // Temporary fragment
                    // Startinng an Fragment in question_container
                    com.example.ehsanullah.loginandregistration.openness.getFavouriteSinger question = new com.example.ehsanullah.loginandregistration.openness.getFavouriteSinger(favSinger);
                    android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.question_container, question);
                    transaction.commit();


                    //___________________ storing the favourite painter value in firebase __________________
                    Constant.FIREBASE_REF.child("questionnaireResults").child(Constant.CURRENT_USER_QUESTIONNAIRE_ID)
                            .child("favPainter").setValue(etFavPainter.getText().toString());

                } else {
                    Toast.makeText(getActivity(), "Please enter the Movie name!", Toast.LENGTH_SHORT).show();
                }

            }

//                Constant.opennessAnswersList.add(7,"1");

        });


        return view;
    }
}




