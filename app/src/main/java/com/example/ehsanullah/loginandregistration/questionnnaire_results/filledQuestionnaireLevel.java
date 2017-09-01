package com.example.ehsanullah.loginandregistration.questionnnaire_results;

/**
 * Created by Ehsan Ullah on 7/21/2017.
 */

public class filledQuestionnaireLevel {

    Boolean opennessFilled, conscientiousnessFilled, extraversionFilled, agreeablenessFilled, neutrocismFilled;

    public filledQuestionnaireLevel() {
    }

    public filledQuestionnaireLevel(Boolean opennessFilled, Boolean conscientiousnessFilled, Boolean extraversionFilled, Boolean agreeablenessFilled, Boolean neutrocismFilled) {
        this.opennessFilled = opennessFilled;
        this.conscientiousnessFilled = conscientiousnessFilled;
        this.extraversionFilled = extraversionFilled;
        this.agreeablenessFilled = agreeablenessFilled;
        this.neutrocismFilled = neutrocismFilled;
    }

    public Boolean getOpennessFilled() {
        return opennessFilled;
    }

    public void setOpennessFilled(Boolean opennessFilled) {
        this.opennessFilled = opennessFilled;
    }

    public Boolean getConscientiousnessFilled() {
        return conscientiousnessFilled;
    }

    public void setConscientiousnessFilled(Boolean conscientiousnessFilled) {
        this.conscientiousnessFilled = conscientiousnessFilled;
    }

    public Boolean getExtraversionFilled() {
        return extraversionFilled;
    }

    public void setExtraversionFilled(Boolean extraversionFilled) {
        this.extraversionFilled = extraversionFilled;
    }

    public Boolean getAgreeablenessFilled() {
        return agreeablenessFilled;
    }

    public void setAgreeablenessFilled(Boolean agreeablenessFilled) {
        this.agreeablenessFilled = agreeablenessFilled;
    }

    public Boolean getNeutrocismFilled() {
        return neutrocismFilled;
    }

    public void setNeutrocismFilled(Boolean neutrocismFilled) {
        this.neutrocismFilled = neutrocismFilled;
    }
}
