package com.example.cook;

/**
 * Created by guojian on 10/9/16.
 */
public class CookStepBean {
    private String stepp;
    private String stepimg_url;

    public CookStepBean(String stepp, String stepimg_url) {
        super();
        this.stepimg_url = stepimg_url;
        this.stepp = stepp;
    }

    public String getStepp() {
        return stepp;
    }

    public void setStepp(String stepp) {
        this.stepp = stepp;
    }

    public String getStepimg_url() {
        return stepimg_url;
    }

    public void setStepimg_url(String stepimg_url) {
        this.stepimg_url = stepimg_url;
    }
}
