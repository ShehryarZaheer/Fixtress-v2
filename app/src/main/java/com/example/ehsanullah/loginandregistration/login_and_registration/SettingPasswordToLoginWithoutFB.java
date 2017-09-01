package com.example.ehsanullah.loginandregistration.login_and_registration;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

/**
 * Created by Ehsan Ullah on 6/12/2017.
 */

public class SettingPasswordToLoginWithoutFB extends Fragment {

    private Button btnSetPassword;
    private ImageView ivTogglePassword, ivToggleConfirmPassword;
    private TextView tvEmail;
    private EditText etPassword, etConfirmPassword;
    private Firebase firebaseRef, usersRef1;
    private ProgressDialog pd;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.setting_password_to_login_without_fb, container, false);


        //_______________________________ Views _________________________________
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) view.findViewById(R.id.etConfirmPassword);
        ivTogglePassword = (ImageView) view.findViewById(R.id.ivTogglePassword);
        ivToggleConfirmPassword = (ImageView) view.findViewById(R.id.ivToggleConfirmPassword);
        btnSetPassword = (Button) view.findViewById(R.id.btnSetPassword);


        //_______________________________ To seen and unseen the password in EditText _________________________________
        ivTogglePassword.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });


        //_______________________________ To seen and unseen the Confirm password in EditText _________________________________
        ivToggleConfirmPassword.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });


        //_______________________________ Displaying the Email Address you are logged in with _________________________________
        tvEmail.setText(Constant.CURRENT_USERS_EMAIL);


        //________________ opening the dialog to get the friend or family num to let them know about user depression __________
        GetFriendOrFamilyNumDialog fofn=new GetFriendOrFamilyNumDialog(getActivity());
        fofn.show();


        btnSetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {

                    //_______________________________ Starting the ProgressDialog _________________________________
                    pd = ProgressDialog.show(getActivity(), "Please wait...", "");


                    //_______________________________ Query to get the all the data of a specific user w.r.t Email _________________________________
                    Query mobileNumExistanceQuery = Constant.FIREBASE_REF.child("users").orderByChild("email").equalTo(Constant.CURRENT_USERS_EMAIL);
                    mobileNumExistanceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //_______________________________ check if server return null _________________________________
                            if (dataSnapshot.exists()) {

                                //getting the data at specific node which is selected by input Email
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    Constant.CURRENT_USERS_ID = child.getKey().toString();
                                }

                                //_______________________________ Updating a specific node of CurrentUser _________________________________
                                dataSnapshot.getRef().child(Constant.CURRENT_USERS_ID).child("password").setValue(etPassword.getText().toString());
                                dataSnapshot.getRef().child(Constant.CURRENT_USERS_ID).child("passwordSettedToLoginWithoutFB").setValue(true);


                                Toast.makeText(getActivity(), "Password saved!", Toast.LENGTH_SHORT).show();

                                //_______________________________ Dismiss the ProgressDialog _________________________________
                                pd.dismiss();


                                //_____________________________ start the questionnaire From beginning ______________________________
                                startingQuestionnaireFunc();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            Toast.makeText(getActivity(), firebaseError.toString(), Toast.LENGTH_SHORT).show();
                        }

                    });
                } else {
                    Toast.makeText(getActivity(), "Password Does'nt match!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    private void startingQuestionnaireFunc() {

        //_____________________________ Questionnaire starts ______________________________

        //for openness start
        String q1 = "(Name)I have heard alot that people who are really rich are those who got really innovative and excellent ideas ... " +
                "do you think you have excellent ideas in your mind?";

        // Startinng an Fragment in question_container
        com.example.ehsanullah.loginandregistration.openness.Question1 question = new com.example.ehsanullah.loginandregistration.openness.Question1(q1);
        android.app.FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
        transaction1.replace(R.id.question_container, question);
        transaction1.commit();
    }


}
