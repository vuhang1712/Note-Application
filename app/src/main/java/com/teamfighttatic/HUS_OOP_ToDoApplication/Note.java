package com.teamfighttatic.HUS_OOP_ToDoApplication;

import com.google.gson.annotations.SerializedName;
import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Ibase;

import java.util.ArrayList;

public class Note extends Ibase {
    @SerializedName("name")
    private  String name;
    @SerializedName("tag")
    private ArrayList<Tag> tag;
    @SerializedName("isComplete")
    private Boolean isComplete;
    @SerializedName("isStar")
    private Boolean isStar;
    @SerializedName("_id")
    private String _id;

    public Note(String name, ArrayList<Tag> tag, Boolean isComplete, Boolean isStar) {
        this.name = name;
        this.tag = tag;
        this.isComplete = isComplete;
        this.isStar = isStar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(ArrayList<Tag> tag) {
        this.tag = tag;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public void setStar(Boolean star) {
        isStar = star;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Tag> getTag() {
        return tag;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public Boolean getStar() {
        return isStar;
    }

    public String get_id() {
        return _id;
    }

    public Note(String name) {
        this.name = name;
    }

}
