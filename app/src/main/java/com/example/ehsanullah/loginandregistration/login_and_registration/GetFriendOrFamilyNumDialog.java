package com.example.ehsanullah.loginandregistration.login_and_registration;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.R;

/**
 * Created by Ehsan Ullah on 7/31/2017.
 */

public class GetFriendOrFamilyNumDialog extends Dialog{

    private EditText etFriendOrFamilyMobileNum;
    private TextView tvSkipFriendOrFamilyNum;
    private Boolean passwordSettedToLoginWithoutFB;

    public GetFriendOrFamilyNumDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_friend_or_family_num_dialog);


        etFriendOrFamilyMobileNum = (EditText) findViewById(R.id.etFriendOrFamilyMobileNum);
        tvSkipFriendOrFamilyNum = (TextView) findViewById(R.id.tvSkipFriendOrFamilyNum);
        Button btnNext = (Button) findViewById(R.id.btnNext);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //_________________ storing the num in firebase __________________
                Constant.FIREBASE_REF.child("users").child(Constant.CURRENT_USERS_ID).child("friendOrFamilyNum")
                        .setValue(etFriendOrFamilyMobileNum.getText().toString());

                dismiss();
            }
        });


        tvSkipFriendOrFamilyNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();

            }
        });

    }
}
