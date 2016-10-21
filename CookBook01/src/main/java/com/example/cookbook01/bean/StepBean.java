package com.example.cookbook01.bean;

import java.io.Serializable;

/**
 * Created by guojian on 10/9/16.
 */
public class StepBean implements Serializable {
    private String step_text;
    private String step_img_url;

    public StepBean() {
    }

    public StepBean(String step_img_url, String step_text) {
        this.step_img_url = step_img_url;
        this.step_text = step_text;
    }

    public String getStep_img_url() {
        return step_img_url;
    }

    public void setStep_img_url(String step_img_url) {
        this.step_img_url = step_img_url;
    }

    public String getStep_text() {
        return step_text;
    }

    public void setStep_text(String step_text) {
        this.step_text = step_text;
    }
}
