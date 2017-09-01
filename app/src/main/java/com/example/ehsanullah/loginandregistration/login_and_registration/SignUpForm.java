package com.example.ehsanullah.loginandregistration.login_and_registration;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
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
 * Created by Ehsan Ullah on 4/15/2017.
 */

public class SignUpForm extends Fragment implements View.OnClickListener {

    private EditText etFirstName, etLastName, etMobileNum, etEmail, etPassword, etConfirmPassword;
    private TextView tvAlreadyRegistered, tvDOB;
    private Button btnDOB, btnSignUp;
    private ImageView ivTogglePassword,ivToggleConfirmPassword;
    private SharedPreferences sharedPreferences;
    private Firebase firebaseRef, ousersRef;
    private String gender = "";
    private Boolean pregnant = false;
    private java.text.DateFormat formate = java.text.DateFormat.getDateInstance();
    private java.util.Calendar calendar = java.util.Calendar.getInstance();
    private CheckBox cbPregnant, cbTermsAndConditions;
    ProgressDialog pd;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.sign_up_form, container, false);


        //_______________________________ Views _________________________________
        etFirstName = (EditText) view.findViewById(R.id.etFirstName);
        etLastName = (EditText) view.findViewById(R.id.etLastName);
        etMobileNum = (EditText) view.findViewById(R.id.etMobileNum);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) view.findViewById(R.id.etConfirmPassword);
        ivTogglePassword = (ImageView) view.findViewById(R.id.ivTogglePassword);
        ivToggleConfirmPassword = (ImageView) view.findViewById(R.id.ivToggleConfirmPassword);
        tvDOB = (TextView) view.findViewById(R.id.tvDOB);
        cbPregnant = (CheckBox) view.findViewById(R.id.cbPregnant);
        cbTermsAndConditions = (CheckBox) view.findViewById(R.id.cbTermsAndConditions);
        btnDOB = (Button) view.findViewById(R.id.btnDOB);
        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        tvAlreadyRegistered = (TextView) view.findViewById(R.id.tvAlreadyRegistered);


        //_______________________________ Apllying OnClickListener _________________________________
        btnDOB.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        tvAlreadyRegistered.setOnClickListener(this);


        //_______________________________ Initializing the SharedPreferences _________________________________
        sharedPreferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);


//        //_______________________________ Firebase Connection _________________________________
//        Firebase.setAndroidContext(getActivity());
//        firebaseRef = new Firebase("https://fixtressdatabase.firebaseio.com/");
//        usersRef = firebaseRef.child("users");


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


        //_______________________________ getting the value for the gender of the user _________________________________
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rgGender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rbMale) {
                    gender = "male";
                    cbPregnant.setVisibility(view.INVISIBLE);
                } else if (checkedId == R.id.rbFemale) {
                    gender = "female";
                    cbPregnant.setVisibility(view.VISIBLE);
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDOB:
                setDateFunc();
                break;

            case R.id.btnSignUp:


                //_______________________________ Starting up ProgressDialog _________________________________
                pd = ProgressDialog.show(getActivity(), "Creating Account...", "");


                //_______________________________ check for empty fields in SignUp Form _________________________________
                if (etFirstName.getText().toString().equals("")) {

                    pd.dismiss();
                    Toast.makeText(getActivity(), "Please enter First Name!", Toast.LENGTH_SHORT).show();

                } else if (etLastName.getText().toString().equals("")) {

                    pd.dismiss();
                    Toast.makeText(getActivity(), "Please enter Last Name!", Toast.LENGTH_SHORT).show();

                } else if (etEmail.getText().toString().equals("")) {

                    pd.dismiss();
                    Toast.makeText(getActivity(), "Please enter Email!", Toast.LENGTH_SHORT).show();

                } else if (!etEmail.getText().toString().contains("@")) {

                    pd.dismiss();
                    Toast.makeText(getActivity(), "Please enter valid Email Address!", Toast.LENGTH_SHORT).show();

                } else if (etPassword.getText().toString().equals("")) {

                    pd.dismiss();
                    Toast.makeText(getActivity(), "Please enter Password!", Toast.LENGTH_SHORT).show();

                } else if (etConfirmPassword.getText().toString().equals("")) {

                    pd.dismiss();
                    Toast.makeText(getActivity(), "Please Confirm Password!", Toast.LENGTH_SHORT).show();

                } else if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {

                    pd.dismiss();
                    Toast.makeText(getActivity(), "Password does not Match!", Toast.LENGTH_SHORT).show();

                } else if (tvDOB.getText().toString().equals("Jan 0, 0000")) {

                    pd.dismiss();
                    Toast.makeText(getActivity(), "Please set your Date of Birth!", Toast.LENGTH_SHORT).show();

                } else if (gender.equals("")) {

                    pd.dismiss();
                    Toast.makeText(getActivity(), "Please select your gender!", Toast.LENGTH_SHORT).show();

                } else if (cbTermsAndConditions.isChecked() == false) {
                    pd.dismiss();
                    Toast.makeText(getActivity(), "Please check the Terms and Conditions!", Toast.LENGTH_SHORT).show();
                } else {

                    signUpFunc();
                }

                break;

            case R.id.tvAlreadyRegistered:
                gotoLoginPageFunc();
                break;
        }
    }

    private void signUpFunc() {

        //_______________________________ Query to get the all the data of a specific user w.r.t Email _________________________________
        Query emailExistenceQuery = Constant.FIREBASE_REF.child("users").orderByChild("email").equalTo(etEmail.getText().toString());
        emailExistenceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //_______________________________ check if server return null _________________________________
                if (dataSnapshot.exists()) {
                    //_______________________________ Dismiss the ProgressDialog _________________________________
                    pd.dismiss();

                    Toast.makeText(getActivity(), "This Email is already in use!", Toast.LENGTH_SHORT).show();
                } else {

                    if(etMobileNum.getText().toString().equals(""))
                    {
                        signUpProcessFunc();
                    }
                    else
                    {
                        checkMobileNumExistenceFunc();
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getActivity(), firebaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkMobileNumExistenceFunc() {

        //_______________________________ Query to get the all the data of a specific user w.r.t Mobile num _________________________________
        Query mobileNumExistanceQuery = Constant.FIREBASE_REF.child("users").orderByChild("mobileNum").equalTo(etMobileNum.getText().toString());
        mobileNumExistanceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //_______________________________ check if server return null _________________________________
                if (dataSnapshot.exists()) {
                    //_______________________________ Dismiss the ProgressDialog _________________________________
                    pd.dismiss();

                    Toast.makeText(getActivity(), "This Mobile Number is already in use!", Toast.LENGTH_SHORT).show();
                } else {

                    signUpProcessFunc();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getActivity(), firebaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signUpProcessFunc() {


        //_______________________________ Pregnancy Check _________________________________
        if (cbPregnant.isChecked()) {
            pregnant = true;
        } else {
            pregnant = false;
        }


        //_______________________________ Storing the data in Firebase _________________________________
        Constant.CURRENT_USERS_ID = Constant.FIREBASE_REF.child("users").push().getKey();

        Constant.FIREBASE_REF.child("users").child(Constant.CURRENT_USERS_ID).setValue(new User(etFirstName.getText().toString(), etLastName.getText().toString(),
                etMobileNum.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString(),
                gender, tvDOB.getText().toString(),""/*friendOrFamilyNum*/, pregnant,true/*signUpWithoutFB*/,true/*passwordSettedToLoginWithoutFB*/, false/*questionnaireFilled*/, Constant.HAS_SENSOR));

        //String key = usersRef.push().getKey().toString();

        // Getting the Device_ID
//        androidId = Settings.Secure.getString(getActivity().getContentResolver(),
//                Settings.Secure.ANDROID_ID);


        //_______________________________ Storing the data in sharedprefrences _________________________________
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mobileNum", etMobileNum.getText().toString());
        editor.putString("email", etEmail.getText().toString());
        editor.putString("password", etPassword.getText().toString());
        editor.putBoolean("questionnaire_filled",false);
        editor.putString("current_user_id",Constant.CURRENT_USERS_ID);
        //editor.putString("device_id",androidId.toString());
        //editor.putString("currentUserPushedKey",key);
        editor.commit();


        //_______________________________ Dismiss the ProgressDialog _________________________________
        pd.dismiss();


        Toast.makeText(getActivity(), "Successfully Registered!", Toast.LENGTH_SHORT).show();


        //_______________________________ return to login page _________________________________
        gotoLoginPageFunc();
    }

    private void gotoLoginPageFunc() {

        //________________ opening the dialog to get the friend or family num to let them know about user depression __________
        GetFriendOrFamilyNumDialog fofn=new GetFriendOrFamilyNumDialog(getActivity());
        fofn.show();


        LoginForm lf = new LoginForm();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, lf);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void updateDateFunc() {
        tvDOB.setText(formate.format(calendar.getTime()));
    }

    public void setDateFunc() {
        new DatePickerDialog(getActivity(), dp, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }


    //_______________________________ Datepicker _________________________________
    DatePickerDialog.OnDateSetListener dp = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateFunc();
        }
    };
}
