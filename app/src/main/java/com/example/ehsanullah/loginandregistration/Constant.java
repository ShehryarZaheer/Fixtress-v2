package com.example.ehsanullah.loginandregistration;

import com.firebase.client.Firebase;

import java.util.ArrayList;

/**
 * Created by Ehsan Ullah on 6/5/2017.
 */

public class Constant {

    public static Firebase FIREBASE_REF = null;
    public static Firebase FIREBASE_FACEBOOK_DATA_REF = null;
    public static Firebase FIREBASE_QUESTIONNAIRE_DATA_REF = null;

    //____________________________________ User's Personal Info _______________________________
    public static String CURRENT_USERS_ID = "";
    public static String CURRENT_USERS_FIRST_NAME = "";
    public static String CURRENT_USERS_Last_NAME = "";
    public static String CURRENT_USERS_MOBILE_NUM = "";
    public static String CURRENT_USERS_EMAIL = "";
    public static String CURRENT_USERS_PASSWORD = "";
    public static String CURRENT_USERS_DOB = "";
    public static String CURRENT_USERS_GENDER = "";
    public static boolean CURRENT_ACCOUNTS_USER_PREGNANT = false;
    public static boolean LOGIN_WITH_FACEBOOK = false;
    public static boolean QUESTIONNAIRE_FILLED = false;
    public static boolean HAS_SENSOR = false;
    public static boolean SIGN_UP_WITHOUT_FACEBOOK = false;


    public static String CURRENT_USER_QUESTIONNAIRE_ID = "";
    public static ArrayList<String> opennessAnswersList = new ArrayList<String>();
    public static ArrayList<String> conscientiousnessAnswersList = new ArrayList<String>();
    public static ArrayList<String> extraversionAnswersList = new ArrayList<String>();
    public static ArrayList<String> agreeablenessAnswersList = new ArrayList<String>();
    public static ArrayList<String> neutrocismAnswersList = new ArrayList<String>();
    public static int NUM_OF_ATTEMPTED_QUESTIONS = 0;


}
