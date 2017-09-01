package com.example.ehsanullah.loginandregistration.login_and_registration;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.questionnnaire_results.questionnaireResults;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

/**
 * Created by Ehsan Ullah on 4/15/2017.
 */

public class LoginForm extends Fragment implements View.OnClickListener {

    public EditText etMobileNumOrEmail, etPassword;
    private TextView tvForgetPassword, tvLoginWithFB;
    private Button btnLogin, btnRegister;
    private ImageView ivTogglePassword;
    private SharedPreferences sharedPreferences;
    private String mobileNumToSendPassword, emailToSendPassword, passwordToSend;
    private ProgressDialog pd;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_form, container, false);


        //_______________________________ Views _________________________________
        etMobileNumOrEmail = (EditText) view.findViewById(R.id.etMobileNumOrEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        ivTogglePassword = (ImageView) view.findViewById(R.id.ivTogglePassword);
        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        tvLoginWithFB = (TextView) view.findViewById(R.id.tvLoginWithFB);
        tvForgetPassword = (TextView) view.findViewById(R.id.tvForgetPassword);
        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);


        //_______________________________ Setting OnClickLIsteners _________________________________
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvLoginWithFB.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);


        //_______________________________ underline on tvForgetPassword _________________________________
        tvForgetPassword.setPaintFlags(tvForgetPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


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


        return view;
    }


    @Override
    public void onClick(View v) {

        //_______________________________ check for internet connected or not _________________________________
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo activeConnection = connectivityManager.getActiveNetworkInfo();
        boolean isOnline = (activeConnection != null) && activeConnection.isConnected();

        if (isOnline) {

            switch (v.getId()) {

                case R.id.btnLogin:

                    if (!etMobileNumOrEmail.getText().toString().equals("") && !etPassword.getText().toString().equals("")) {

                        //_______________________________ Starting the ProgressDialog _________________________________
                        pd = ProgressDialog.show(getActivity(), "Logging In...", "");

                        if (etMobileNumOrEmail.getText().toString().contains("@")) {
                            loginThroughEmailFunc();
                        } else {
                            loginThroughMobileNumFunc();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Mobile Number and Password are Required!", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.btnRegister:

                    //_______________________________ going to the SignUp Form _________________________________
                    SignUpForm suf = new SignUpForm();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, suf);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    break;

                case R.id.tvForgetPassword:

                    if (!etMobileNumOrEmail.getText().toString().equals("")) {

                        //_______________________________ Starting the ProgressDialog _________________________________
                        pd = ProgressDialog.show(getActivity(), "Please Wait...", "");


                        //_______________________________ Check that given Mobile num exists in Database or not _________________________________
                        mobileNumOrEmailExistanceCheckFunc();
                    } else {
                        Toast.makeText(getActivity(), "Please enter Mobile Number or Email!", Toast.LENGTH_LONG).show();
                    }

                    break;

                case R.id.tvLoginWithFB:

                    //_______________________________ opening the Activity for Login with Facebook _________________________________
                    Intent intent = new Intent(getActivity(), LoginWithFacebookActivity.class);
                    startActivity(intent);

                    break;
            }

        } else {
            Toast.makeText(getActivity(), "You are not connected to Internet!", Toast.LENGTH_SHORT).show();
        }
    }


    private void loginThroughEmailFunc() {

        // Getting the Device_ID
//                currentAndroidId = Settings.Secure.getString(getActivity().getContentResolver(),
//                        Settings.Secure.ANDROID_ID);


        //_______________________________ check for valid Email and password _________________________________
        if (etMobileNumOrEmail.getText().toString().equals(sharedPreferences.getString("email", "").toString())
                && etPassword.getText().toString().equals(sharedPreferences.getString("password", "").toString())) {
            Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();


            Constant.CURRENT_USERS_EMAIL = sharedPreferences.getString("email", "").toString();
            Constant.CURRENT_USERS_ID = sharedPreferences.getString("current_user_id", "").toString();


            //_______________________________ Dismiss the ProgressDialog _________________________________
            pd.dismiss();


            if (sharedPreferences.getBoolean("questionnaire_filled", false) == false) {
                //_____________________________ getting the Questionnaire_ID from Firebase ______________________________
                //startQuestionnaireWhereYouLeftFunc();


                //_____________________________ starting the questionnaire where you left ______________________________
                Intent intent = new Intent(getActivity(), questionnaireActivity.class);// we used this activity because there was error in creating fragment in 'fragment_container' thats why we create a new 'question_container'
                intent.putExtra("passwordSettedToLoginWithoutFB", true);
                startActivity(intent);

            } else {
                Toast.makeText(getActivity(), "Questionnaire already filled!", Toast.LENGTH_SHORT).show();
            }


        } else {


            //_______________________________ Query to get the all the data of a specific user w.r.t Email _________________________________
            Query emailExistanceQuery = Constant.FIREBASE_REF.child("users").orderByChild("email").equalTo(etMobileNumOrEmail.getText().toString());
            emailExistanceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //_______________________________ whole process of logging in _________________________________
                    loginThroughEmailFromFirebaseProcessFunc(dataSnapshot);


                    //_______________________________ Dismiss the ProgressDialog _________________________________
                    pd.dismiss();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(getActivity(), firebaseError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void startQuestionnaireWhereYouLeftFunc() {

        if (Constant.QUESTIONNAIRE_FILLED == false) {

            //________________ assigning values to all ArrayLists of questionnaire results, to set the value on each index ___________
            for (int i = 0; i < 10; i++) {
                Constant.agreeablenessAnswersList.add("");
                Constant.conscientiousnessAnswersList.add("");
                Constant.extraversionAnswersList.add("");
                Constant.neutrocismAnswersList.add("");
                Constant.opennessAnswersList.add("");
            }


            //_______________________________ getting Questionnaire_ID (it will use if questionnaire will incomplete) _________________________________
            Query mobileNumExistenceQuery = Constant.FIREBASE_REF.child("questionnaireResults").orderByChild("email").equalTo(Constant.CURRENT_USERS_EMAIL);
            mobileNumExistenceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    questionnaireResults results = null;

                    //_______________________________ check if server return null _________________________________
                    if (dataSnapshot.exists()) {

                        //getting the data at specific node which is selected by input Email
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Constant.CURRENT_USER_QUESTIONNAIRE_ID = child.getKey().toString();
                            results = child.getValue(questionnaireResults.class);
                        }


                        //____________ starts the
                        if (results.getFilledQuestionnaireLevel().getOpennessFilled().equals(true) &&
                                results.getFilledQuestionnaireLevel().getConscientiousnessFilled().equals(false)) {

                            //for openness start
                            String q1 = "do you like things in order?";

                            // Startinng an Fragment in fragment_container
                            com.example.ehsanullah.loginandregistration.conscientiousness.Question1 question = new com.example.ehsanullah.loginandregistration.conscientiousness.Question1(q1);
                            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, question);
                            transaction.commit();

                        } else if (results.getFilledQuestionnaireLevel().getConscientiousnessFilled().equals(true) &&
                                results.getFilledQuestionnaireLevel().getExtraversionFilled().equals(false)) {

                            //for Conscientiousness start
                            String q1 = "Let say you are attending any marriage and you are looking most beautiful among all the other people and they are looking and talking about you, you are like the hub of attention of all people\n" +
                                    "\t... what do you feel like at that time ...";

                            // Startinng an Fragment in fragment_container
                            com.example.ehsanullah.loginandregistration.extraversion.Question1 question = new com.example.ehsanullah.loginandregistration.extraversion.Question1(q1);
                            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, question);
                            transaction.commit();

                        } else if (results.getFilledQuestionnaireLevel().getExtraversionFilled().equals(true) &&
                                results.getFilledQuestionnaireLevel().getAgreeablenessFilled().equals(false)) {

                            //for agreeableness start
                            String q1 = "(name) did it ever happened that any of your friend or any other person you know " +
                                    "told you about his worry and after listening him you got worried too?";

                            // Startinng an Fragment in fragment_container
                            com.example.ehsanullah.loginandregistration.agreeableness.Question2 question = new com.example.ehsanullah.loginandregistration.agreeableness.Question2(q1);
                            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, question);
                            transaction.commit();

                        } else if (results.getFilledQuestionnaireLevel().getAgreeablenessFilled().equals(true) &&
                                results.getFilledQuestionnaireLevel().getNeutrocismFilled().equals(false)) {

                            //for neutrocism start
                            String q1 = "(Name)I don't want you get bored or irritated while communicating with me thats why i wanna know do you get irritated easily?";

                            // Startinng an Fragment in fragment_container
                            com.example.ehsanullah.loginandregistration.neutrocism.Question1 question = new com.example.ehsanullah.loginandregistration.neutrocism.Question1(q1);
                            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, question);
                            transaction.commit();
                        }


                    } else {

                        //__________________ start the questionnaire from beginning ____________________
                        startingQuestionnaireFunc();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(getActivity(), firebaseError.toString(), Toast.LENGTH_SHORT).show();
                }

            });
        } else {
            Toast.makeText(getActivity(), "Questionnaire Already Filled!", Toast.LENGTH_SHORT).show();
        }
    }

    private void startingQuestionnaireFunc() {

        //_____________________________ Questionnaire starts ______________________________

        //for openness start
        String q1 = "(Name)I have heard alot that people who are really rich are those who got really innovative and excellent ideas ... " +
                "do you think you have excellent ideas in your mind?";

        // Startinng an Fragment in fragment_container
        com.example.ehsanullah.loginandregistration.openness.Question1 question = new com.example.ehsanullah.loginandregistration.openness.Question1(q1);
        android.app.FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
        transaction1.add(R.id.fragment_container, question);
        transaction1.commit();
    }

    private void loginThroughEmailFromFirebaseProcessFunc(DataSnapshot dataSnapshot) {

        User user = null;


        //_______________________________ check if server return null _________________________________
        if (dataSnapshot.exists()) {


            //getting the data at specific node which is selected by input Email
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                Constant.CURRENT_USERS_ID = child.getKey().toString();
                user = child.getValue(User.class);
            }


            //_______________________________ check if input exists in DB _________________________________
            if (user.getEmail().toString().equals(etMobileNumOrEmail.getText().toString()) &&
                    user.getPassword().toString().equals(etPassword.getText().toString())) {
                Toast.makeText(getActivity(), "Successfully Logged In!", Toast.LENGTH_SHORT).show();


                Constant.CURRENT_USERS_EMAIL = user.getEmail().toString();
                Constant.QUESTIONNAIRE_FILLED = user.getQuestionnaireFilled();


                //_______________________________ Storing the data in sharedprefrences _________________________________
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", etMobileNumOrEmail.getText().toString());
                editor.putString("password", etPassword.getText().toString());
                editor.putString("current_user_id", Constant.CURRENT_USERS_ID);
                editor.putBoolean("questionnaire_filled", Constant.QUESTIONNAIRE_FILLED);
                editor.commit();


                //_____________________________ getting the Questionnaire_ID from Firebase ______________________________
//                startQuestionnaireWhereYouLeftFunc();


                if (Constant.QUESTIONNAIRE_FILLED == false) {

                    //_____________________________ starting the questionnaire where you left ______________________________
                    Intent intent = new Intent(getActivity(), questionnaireActivity.class);// we used this activity because there was error in creating fragment in 'fragment_container' thats why we create a new 'question_container'
                    intent.putExtra("passwordSettedToLoginWithoutFB", true);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getActivity(), "Questionnaire already filled!", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(getActivity(), "Email or Password is not correct!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getActivity(), "This Email is not Registered!", Toast.LENGTH_SHORT).show();
        }
    }


    private void loginThroughMobileNumFunc() {

        //_______________________________ Query to get the all the data of a specific user w.r.t mobile number _________________________________
        Query mobileNumExistanceQuery = Constant.FIREBASE_REF.child("users").orderByChild("mobileNum").equalTo(etMobileNumOrEmail.getText().toString());
        mobileNumExistanceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //_______________________________ whole process of logging in _________________________________
                loginThroughMobileNumFromFirebaseProcessFunc(dataSnapshot);


                //_______________________________ Dismiss the ProgressDialog _________________________________
                pd.dismiss();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getActivity(), firebaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // }

    }

    private void loginThroughMobileNumFromFirebaseProcessFunc(DataSnapshot dataSnapshot) {

        User user = null;


        //_______________________________ check if server return null _________________________________
        if (dataSnapshot.exists()) {

            //getting the data at specific node which is selected by input Mobile Number
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                Constant.CURRENT_USERS_ID = child.getKey().toString();
                user = child.getValue(User.class);
            }


            //_______________________________ check if input exists in DB _________________________________
            if (user.getMobileNum().toString().equals(etMobileNumOrEmail.getText().toString()) &&
                    user.getPassword().toString().equals(etPassword.getText().toString())) {
                Toast.makeText(getActivity(), "Successfully Logged In!", Toast.LENGTH_SHORT).show();


                Constant.CURRENT_USERS_EMAIL = user.getEmail().toString();
                Constant.QUESTIONNAIRE_FILLED = user.getQuestionnaireFilled();

                //_______________________________ Storing the data in sharedprefrences _________________________________
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", Constant.CURRENT_USERS_EMAIL);
                editor.putString("mobileNum", etMobileNumOrEmail.getText().toString());
                editor.putString("password", etPassword.getText().toString());
                editor.putString("current_user_id", Constant.CURRENT_USERS_ID);
                editor.putBoolean("questionnaire_filled", Constant.QUESTIONNAIRE_FILLED);
                editor.commit();


                //_____________________________ getting the Questionnaire_ID from Firebase ______________________________
                //startQuestionnaireWhereYouLeftFunc();


                //_____________________________ starting the questionnaire where you left ______________________________
                Intent intent = new Intent(getActivity(), questionnaireActivity.class);// we used this activity because there was error in creating fragment in 'fragment_container' thats why we create a new 'question_container'
                intent.putExtra("passwordSettedToLoginWithoutFB", true);
                startActivity(intent);


            } else {
                Toast.makeText(getActivity(), "Mobile Number or Password is not correct!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getActivity(), "This Mobile number is not Registered!", Toast.LENGTH_SHORT).show();
        }
    }


    private void mobileNumOrEmailExistanceCheckFunc() {

        //_______________________________ Query to get the all the data of a specific user w.r.t mobile number _________________________________
        Query mobileNumOrEmailExistanceQuery;

        if (etMobileNumOrEmail.getText().toString().contains("@")) {
            mobileNumOrEmailExistanceQuery = Constant.FIREBASE_REF.child("users").orderByChild("email").equalTo(etMobileNumOrEmail.getText().toString());
        } else {
            mobileNumOrEmailExistanceQuery = Constant.FIREBASE_REF.child("users").orderByChild("mobileNum").equalTo(etMobileNumOrEmail.getText().toString());
        }

        mobileNumOrEmailExistanceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (etMobileNumOrEmail.getText().toString().contains("@")) {
                    //_______________________________ the main process of checking the Email existance _________________________________
                    EmailValidationProcessFunc(dataSnapshot);
                } else {
                    //_______________________________ the main process of checking the mobile num existance _________________________________
                    MobileNumValidationProcessFunc(dataSnapshot);
                }


                //_______________________________ Dismiss the ProgressDialog _________________________________
                pd.dismiss();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getActivity(), firebaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void MobileNumValidationProcessFunc(DataSnapshot dataSnapshot) {

        User user = null;


        //_______________________________ check if server return null _________________________________
        if (dataSnapshot.exists()) {


            //_______________________________ getting the data at specific node which is selected by input Mobile Number _________________________________
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                user = child.getValue(User.class);
            }

            //_______________________________ getting the values from DB and storing to Constants which will use after _________________________________
            mobileNumToSendPassword = user.getMobileNum().toString();
            emailToSendPassword = user.getEmail().toString();
            passwordToSend = user.getPassword().toString();


            //_______________________________ opening the Dialog to send Forgotten Password _________________________________
            ForgetPasswordDialog fpdc = new ForgetPasswordDialog(mobileNumToSendPassword, emailToSendPassword, passwordToSend);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction1 = fm.beginTransaction();
            transaction1.addToBackStack(null);
            fpdc.show(transaction1, "forget_password_dialog");


        } else {
            Toast.makeText(getActivity(), "This Mobile number is not Registered!", Toast.LENGTH_SHORT).show();
        }
    }


    private void EmailValidationProcessFunc(DataSnapshot dataSnapshot) {

        User user = null;


        //_______________________________ check if server return null _________________________________
        if (dataSnapshot.exists()) {


            //_______________________________ getting the data at specific node which is selected by input Email _________________________________
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                user = child.getValue(User.class);
            }

            //_______________________________ getting the values from DB and storing to Constants which will use after _________________________________
            mobileNumToSendPassword = user.getMobileNum().toString();
            emailToSendPassword = user.getEmail().toString();
            passwordToSend = user.getPassword().toString();


            //_______________________________ opening the Dialog to send Forgotten Password _________________________________
            ForgetPasswordDialog fpdc = new ForgetPasswordDialog(mobileNumToSendPassword, emailToSendPassword, passwordToSend);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction1 = fm.beginTransaction();
            transaction1.addToBackStack(null);
            fpdc.show(transaction1, "forget_password_dialog");


        } else {
            Toast.makeText(getActivity(), "This Email is not Registered!", Toast.LENGTH_SHORT).show();
        }
    }
}
