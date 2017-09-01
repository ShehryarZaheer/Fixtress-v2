package com.example.ehsanullah.loginandregistration.questionnnaire_results;

/**
 * Created by Ehsan Ullah on 7/20/2017.
 */

public class questionnaireResults {

    String email, favMovies, favReadings, favPet, favPainter, favSinger;
    int numOfAttemptedQuestions;

    boolean sensitiveToBeauty, willingToTryNewThings, creative, exploringNewCulture, poetry, art,
            disciplinedAndOrganized, dutiful, perfectionism,attentionSeeking, social, feelingEnergeticAroundOthers,
            kind,trustworthy, trustingOthers, friendly,depression, getStressedEasily, emotionallyStable,fearless,
            shamelessness, negativeFeelings;


    filledQuestionnaireLevel filledQuestionnaireLevel = new filledQuestionnaireLevel();

    public questionnaireResults() {
    }

    public questionnaireResults(String email, boolean sensitiveToBeauty, boolean willingToTryNewThings, boolean creative, boolean exploringNewCulture, boolean poetry, boolean art) {
        this.email = email;
        this.sensitiveToBeauty = sensitiveToBeauty;
        this.willingToTryNewThings = willingToTryNewThings;
        this.creative = creative;
        this.exploringNewCulture = exploringNewCulture;
        this.poetry = poetry;
        this.art = art;
    }

    public questionnaireResults(filledQuestionnaireLevel filledQuestionnaireLevel) {
        this.filledQuestionnaireLevel = filledQuestionnaireLevel;
    }

    public questionnaireResults(String email, String favMovies, filledQuestionnaireLevel filledQuestionnaireLevel) {
        this.email = email;
        this.favMovies = favMovies;
        this.filledQuestionnaireLevel = filledQuestionnaireLevel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFavMovies() {
        return favMovies;
    }

    public void setFavMovies(String favMovies) {
        this.favMovies = favMovies;
    }

    public String getFavReadings() {
        return favReadings;
    }

    public void setFavReadings(String favReadings) {
        this.favReadings = favReadings;
    }

    public String getFavPet() {
        return favPet;
    }

    public void setFavPet(String favPet) {
        this.favPet = favPet;
    }

    public String getFavPainter() {
        return favPainter;
    }

    public void setFavPainter(String favPainter) {
        this.favPainter = favPainter;
    }

    public String getFavSinger() {
        return favSinger;
    }

    public void setFavSinger(String favSinger) {
        this.favSinger = favSinger;
    }

    public boolean isSensitiveToBeauty() {
        return sensitiveToBeauty;
    }

    public void setSensitiveToBeauty(boolean sensitiveToBeauty) {
        this.sensitiveToBeauty = sensitiveToBeauty;
    }

    public boolean isWillingToTryNewThings() {
        return willingToTryNewThings;
    }

    public void setWillingToTryNewThings(boolean willingToTryNewThings) {
        this.willingToTryNewThings = willingToTryNewThings;
    }

    public boolean isCreative() {
        return creative;
    }

    public void setCreative(boolean creative) {
        this.creative = creative;
    }

    public boolean isExploringNewCulture() {
        return exploringNewCulture;
    }

    public void setExploringNewCulture(boolean exploringNewCulture) {
        this.exploringNewCulture = exploringNewCulture;
    }

    public boolean isPoetry() {
        return poetry;
    }

    public void setPoetry(boolean poetry) {
        this.poetry = poetry;
    }

    public boolean isArt() {
        return art;
    }

    public void setArt(boolean art) {
        this.art = art;
    }

    public boolean isDisciplinedAndOrganized() {
        return disciplinedAndOrganized;
    }

    public void setDisciplinedAndOrganized(boolean disciplinedAndOrganized) {
        this.disciplinedAndOrganized = disciplinedAndOrganized;
    }

    public boolean isDutiful() {
        return dutiful;
    }

    public void setDutiful(boolean dutiful) {
        this.dutiful = dutiful;
    }

    public boolean isPerfectionism() {
        return perfectionism;
    }

    public void setPerfectionism(boolean perfectionism) {
        this.perfectionism = perfectionism;
    }

    public boolean isAttentionSeeking() {
        return attentionSeeking;
    }

    public void setAttentionSeeking(boolean attentionSeeking) {
        this.attentionSeeking = attentionSeeking;
    }

    public boolean isSocial() {
        return social;
    }

    public void setSocial(boolean social) {
        this.social = social;
    }

    public boolean isFeelingEnergeticAroundOthers() {
        return feelingEnergeticAroundOthers;
    }

    public void setFeelingEnergeticAroundOthers(boolean feelingEnergeticAroundOthers) {
        this.feelingEnergeticAroundOthers = feelingEnergeticAroundOthers;
    }

    public boolean isKind() {
        return kind;
    }

    public void setKind(boolean kind) {
        this.kind = kind;
    }

    public boolean isTrustworthy() {
        return trustworthy;
    }

    public void setTrustworthy(boolean trustworthy) {
        this.trustworthy = trustworthy;
    }

    public boolean isTrustingOthers() {
        return trustingOthers;
    }

    public void setTrustingOthers(boolean trustingOthers) {
        this.trustingOthers = trustingOthers;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public void setFriendly(boolean friendly) {
        this.friendly = friendly;
    }

    public boolean isDepression() {
        return depression;
    }

    public void setDepression(boolean depression) {
        this.depression = depression;
    }

    public boolean isGetStressedEasily() {
        return getStressedEasily;
    }

    public void setGetStressedEasily(boolean getStressedEasily) {
        this.getStressedEasily = getStressedEasily;
    }

    public boolean isEmotionallyStable() {
        return emotionallyStable;
    }

    public void setEmotionallyStable(boolean emotionallyStable) {
        this.emotionallyStable = emotionallyStable;
    }

    public boolean isFearless() {
        return fearless;
    }

    public void setFearless(boolean fearless) {
        this.fearless = fearless;
    }

    public boolean isShamelessness() {
        return shamelessness;
    }

    public void setShamelessness(boolean shamelessness) {
        this.shamelessness = shamelessness;
    }

    public boolean isNegativeFeelings() {
        return negativeFeelings;
    }

    public void setNegativeFeelings(boolean negativeFeelings) {
        this.negativeFeelings = negativeFeelings;
    }

    public filledQuestionnaireLevel getFilledQuestionnaireLevel() {
        return filledQuestionnaireLevel;
    }

    public void setFilledQuestionnaireLevel(filledQuestionnaireLevel filledQuestionnaireLevel) {
        this.filledQuestionnaireLevel = filledQuestionnaireLevel;
    }

    public int getNumOfAttemptedQuestions() {
        return numOfAttemptedQuestions;
    }

    public void setNumOfAttemptedQuestions(int numOfAttemptedQuestions) {
        this.numOfAttemptedQuestions = numOfAttemptedQuestions;
    }
}
