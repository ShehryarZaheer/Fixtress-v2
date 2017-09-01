package com.example.ehsanullah.loginandregistration;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ehsanullah.loginandregistration.login_and_registration.LoginForm;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Firebase firebaseRef, usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //------------------- to hide the Action bar ---------
//        getSupportActionBar().hide();


// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_main);


        //_______________________________ Firebase Connection _________________________
        Firebase.setAndroidContext(this);
        Constant.FIREBASE_REF = new Firebase("https://fixtressdatabase.firebaseio.com/");


        //_____________________________ Opening Login From ____________________
        LoginForm lf = new LoginForm();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, lf);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    //____________________________ pop the fragment on pressing the back button _______________
    @Override
    public void onBackPressed() {

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
