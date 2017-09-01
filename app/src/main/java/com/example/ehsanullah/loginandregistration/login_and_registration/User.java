package com.example.ehsanullah.loginandregistration.login_and_registration;

/**
 * Created by Ehsan Ullah on 4/15/2017.
 */

public class User {

    String firstName, lastName, mobileNum, email, password, gender, DOB, friendOrFamilyNum;
    Boolean pregnant, signUpWithoutFacebook, passwordSettedToLoginWithoutFB, questionnaireFilled, hasSensor;

    public User() {
    }

    public User(String firstName, String lastName, String mobileNum, String email, String password, String gender, String DOB, String friendOrFamilyNum, Boolean pregnant, Boolean signUpWithoutFacebook, Boolean passwordSettedToLoginWithoutFB, Boolean questionnaireFilled, Boolean hasSensor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNum = mobileNum;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.DOB = DOB;
        this.friendOrFamilyNum = friendOrFamilyNum;
        this.pregnant = pregnant;
        this.signUpWithoutFacebook = signUpWithoutFacebook;
        this.passwordSettedToLoginWithoutFB = passwordSettedToLoginWithoutFB;
        this.questionnaireFilled = questionnaireFilled;
        this.hasSensor = hasSensor;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getFriendOrFamilyNum() {
        return friendOrFamilyNum;
    }

    public void setFriendOrFamilyNum(String friendOrFamilyNum) {
        this.friendOrFamilyNum = friendOrFamilyNum;
    }

    public Boolean getPregnant() {
        return pregnant;
    }

    public void setPregnant(Boolean pregnant) {
        this.pregnant = pregnant;
    }

    public Boolean getSignUpWithoutFacebook() {
        return signUpWithoutFacebook;
    }

    public void setSignUpWithoutFacebook(Boolean signUpWithoutFacebook) {
        this.signUpWithoutFacebook = signUpWithoutFacebook;
    }

    public Boolean getPasswordSettedToLoginWithoutFB() {
        return passwordSettedToLoginWithoutFB;
    }

    public void setPasswordSettedToLoginWithoutFB(Boolean passwordSettedToLoginWithoutFB) {
        this.passwordSettedToLoginWithoutFB = passwordSettedToLoginWithoutFB;
    }

    public Boolean getQuestionnaireFilled() {
        return questionnaireFilled;
    }

    public void setQuestionnaireFilled(Boolean questionnaireFilled) {
        this.questionnaireFilled = questionnaireFilled;
    }

    public Boolean getHasSensor() {
        return hasSensor;
    }

    public void setHasSensor(Boolean hasSensor) {
        this.hasSensor = hasSensor;
    }
}
