package com.example.cookbook01.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by guojian on 10/11/16.
 */
public class CookBean implements Serializable {
    private String title;
    private String img_url;
    private String ingredients;//主料
    private String burden;//辅料
    private List<StepBean> stepBeanList;

    public CookBean(String title, String img_url, String ingredients, String burden, List<StepBean> stepBeanList) {
        this.burden = burden;
        this.img_url = img_url;
        this.ingredients = ingredients;
        this.title = title;
        this.stepBeanList = stepBeanList;
    }

    public String getBurden() {
        return burden;
    }

    public void setBurden(String burden) {
        this.burden = burden;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepBean> getStepBeanList() {
        return stepBeanList;
    }

    public void setStepBeanList(List<StepBean> stepBeanList) {
        this.stepBeanList = stepBeanList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
