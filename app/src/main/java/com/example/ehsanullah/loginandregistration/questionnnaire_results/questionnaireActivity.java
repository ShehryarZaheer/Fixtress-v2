package com.example.ehsanullah.loginandregistration.questionnnaire_results;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ehsanullah.loginandregistration.Constant;
import com.example.ehsanullah.loginandregistration.R;
import com.example.ehsanullah.loginandregistration.login_and_registration.SettingPasswordToLoginWithoutFB;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.example.ehsanullah.loginandregistration.questionnnaire_results.*;

public class questionnaireActivity extends Activity {
    questionnaireResults questionnaireResults = null;


    private static ProgressBar progressBar;
    private static TextView tvNumOfAttemptedQuestions;
    private static Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire_activity);


        //------------------- to hide the Action bar ---------
//        getActionBar().hide();


        //______________________ display how much questions attempted, by ProgressBar ______________
        progressBar= (ProgressBar) findViewById(R.id.Progressbar);
        tvNumOfAttemptedQuestions= (TextView) findViewById(R.id.tvNumOfAttemptedQuestions);
        displayProgressBar();


        if(getIntent().getBooleanExtra("passwordSettedToLoginWithoutFB",false) == false)
        {
            //_______________________________ Opening the Fragment for Setting the Password _________________________________
             SettingPasswordToLoginWithoutFB sp = new  SettingPasswordToLoginWithoutFB();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.question_container,sp);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else
        {
            if(Constant.QUESTIONNAIRE_FILLED == false)
            {

                //________________ assigning values to all ArrayLists of questionnaire results, to set the value on each index ___________
                for (int i = 0; i < 10; i++) {
                    Constant.agreeablenessAnswersList.add("");
                    Constant.conscientiousnessAnswersList.add("");
                    Constant.extraversionAnswersList.add("");
                    Constant.neutrocismAnswersList.add("");
                    Constant.opennessAnswersList.add("");
                }


                //_______________________________ getting Questionnaire_ID (it will use if questionnaire will incomplete) _________________________________
                Query emailExistenceQuery = Constant.FIREBASE_REF.child("questionnaireResults").orderByChild("email").equalTo(Constant.CURRENT_USERS_EMAIL);
                emailExistenceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //_______________________________ check if server return null _________________________________
                        if (dataSnapshot.exists()) {

                            //getting the data at specific node which is selected by input Email
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                Constant.CURRENT_USER_QUESTIONNAIRE_ID = child.getKey().toString();

                                //_________ getting all the questionnaireResults to check from where we have to start questionnaire _____
                                questionnaireResults = child.getValue(questionnaireResults.class);
                            }

                            Constant.NUM_OF_ATTEMPTED_QUESTIONS = questionnaireResults.getNumOfAttemptedQuestions();


                            //__________________ starting the Questionnaire where we left ______________
                            startQuestionnaireWhereYouLeft(questionnaireResults);
                        }
                        else
                        {
                            //__________________ starting the Questionnaire where we left ______________
                            startQuestionnaireWhereYouLeft(questionnaireResults);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Toast.makeText(getApplicationContext(), firebaseError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Questionnaire already filled!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void displayProgressBar() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Constant.NUM_OF_ATTEMPTED_QUESTIONS <42)
                {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(Constant.NUM_OF_ATTEMPTED_QUESTIONS);
                            tvNumOfAttemptedQuestions.setText(Constant.NUM_OF_ATTEMPTED_QUESTIONS +"/"+42);
                        }
                    });
                    try
                    {
                        Thread.sleep(200);

                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void startQuestionnaireWhereYouLeft(questionnaireResults questionnaireResults) {


        if(questionnaireResults == null ||
                (questionnaireResults.getFilledQuestionnaireLevel().getOpennessFilled().equals(false)))
        {
            //_____________________________ Questionnaire starts from beginning ______________________________

            //for com.example.ehsanullah.loginandregistration.openness start
            String q1 = "(Name)I have heard alot that people who are really rich are those who got really innovative and excellent ideas ... " +
                    "do you think you have excellent ideas in your mind?";

            // Startinng an Fragment in question_container
            com.example.ehsanullah.loginandregistration.openness.Question1 question = new com.example.ehsanullah.loginandregistration.openness.Question1(q1);
            FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
            transaction1.add(R.id.question_container, question);
            transaction1.commit();
        }


        //____________ starts the questionnaire where we have left ________________________
         else if (questionnaireResults.getFilledQuestionnaireLevel().getOpennessFilled().equals(true) &&
                questionnaireResults.getFilledQuestionnaireLevel().getConscientiousnessFilled().equals(false)) {

            //for com.example.ehsanullah.loginandregistration.openness start
            String q1 = "Choose one Image";

            // Startinng an Fragment in question_container
             com.example.ehsanullah.loginandregistration.conscientiousness.Question1 question = new  com.example.ehsanullah.loginandregistration.conscientiousness.Question1(q1);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.question_container, question);
            transaction.commit();


        } else if (questionnaireResults.getFilledQuestionnaireLevel().getConscientiousnessFilled().equals(true) &&
                questionnaireResults.getFilledQuestionnaireLevel().getExtraversionFilled().equals(false)) {

            //for Conscientiousness start
            String q1 = "Let say you are attending any marriage and you are looking most beautiful among all the other people and they are looking and talking about you, you are like the hub of attention of all people\n" +
                    "\t... what do you feel like at that time ...";

            // Starting an Fragment in question_container
             com.example.ehsanullah.loginandregistration.extraversion.Question1 question = new  com.example.ehsanullah.loginandregistration.extraversion.Question1(q1);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.question_container, question);
            transaction.commit();


        } else if (questionnaireResults.getFilledQuestionnaireLevel().getExtraversionFilled().equals(true) &&
                questionnaireResults.getFilledQuestionnaireLevel().getAgreeablenessFilled().equals(false)) {

            //for agreeableness start
            String q1 = "(name) did it ever happened that any of your friend or any other person you know " +
                    "told you about his worry and after listening him you got worried too?";

            // Startinng an Fragment in question_container
             com.example.ehsanullah.loginandregistration.agreeableness.Question2 question = new  com.example.ehsanullah.loginandregistration.agreeableness.Question2(q1);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.question_container, question);
            transaction.commit();

        } else if (questionnaireResults.getFilledQuestionnaireLevel().getAgreeablenessFilled().equals(true) &&
                questionnaireResults.getFilledQuestionnaireLevel().getNeutrocismFilled().equals(false)) {

            //for neutrocism start
            String q1 = "(Name)I don't want you get bored or irritated while communicating with me thats why i wanna know do you get irritated easily?";

            // Startinng an Fragment in question_container
             com.example.ehsanullah.loginandregistration.neutrocism.Question1 question = new  com.example.ehsanullah.loginandregistration.neutrocism.Question1(q1);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.question_container, question);
            transaction.commit();

        }

    }



    //____________________________ asking for close the application by dialog, on pressing the back button _______________
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Do you want to close the app?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
