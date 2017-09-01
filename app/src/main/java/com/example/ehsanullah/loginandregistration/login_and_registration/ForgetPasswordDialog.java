package com.example.ehsanullah.loginandregistration.login_and_registration;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ehsanullah.loginandregistration.R;
import com.firebase.client.Firebase;

/**
 * Created by Ehsan Ullah on 5/3/2017.
 */

public class ForgetPasswordDialog extends DialogFragment {

    private String mobileNumToSendPassword,emailToSendPassword, passwordToSend;
    private RadioButton rbMobileNum;
    private RadioButton rbEmail;

    public ForgetPasswordDialog(String mobileNumToSendPassword,String emailToSendPassword, String passwordToSend ) {

        this.mobileNumToSendPassword = mobileNumToSendPassword;
        this.emailToSendPassword = emailToSendPassword;
        this.passwordToSend = passwordToSend;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forget_password_dialog, container, false);


        //_______________________________ Initializing the Views _________________________________
        final FloatingActionButton btnSendForgottenPassword = (FloatingActionButton) view.findViewById(R.id.btnSendForgotenPassword);


        //_______________________________ have to add permission like this in API level 23 and above _________________________________
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.SEND_SMS}, 1);


        //_______________________________ Radio Group for the selection of get Password by Email or Mobile _________________________________
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rgSendPassword);
        rbMobileNum = (RadioButton) view.findViewById(R.id.rbMobileNum);
        rbEmail = (RadioButton) view.findViewById(R.id.rbEmail);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (rbMobileNum.isChecked()) {
                    if(!mobileNumToSendPassword.equals(""))//check if there is mobile num is DB
                    {
                        rbMobileNum.setText("Mobile Number at : " + mobileNumToSendPassword);
                        rbEmail.setText("Email");
                    }
                    else
                    {
                        rbMobileNum.setText("Mobile Number");
                        rbEmail.setText("Email");
                    }
                } else {
                    rbEmail.setText("Email at: " + emailToSendPassword);
                    rbMobileNum.setText("Mobile Number");
                }
            }

        });


        //__________________________________ Opening Dialog for Login after receiving forgotten password ____________________________________
        btnSendForgottenPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //_______________________________ dismiss the opened Dialog _________________________________
                dismiss();


                //_______________________________ the process of sending Forgotten Password through SMS _________________________________
                if (rbMobileNum.isChecked()) {
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(mobileNumToSendPassword, null, passwordToSend, null, null);
                        Toast.makeText(getActivity().getApplicationContext(), "SMS Sent!",
                                Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "SMS failed, please try again later!",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                } else if (rbEmail.isChecked()) {


                    //_______________________________ the process of sending Forgotten Password through Email _________________________________
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{emailToSendPassword});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Use this password to Login.");
                    i.putExtra(Intent.EXTRA_TEXT, passwordToSend);
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));

                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Please select one option.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }



    //_______________________________ To remove the title bar from dialog _________________________________

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        //_______________________________ request a window without the title _________________________________
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}