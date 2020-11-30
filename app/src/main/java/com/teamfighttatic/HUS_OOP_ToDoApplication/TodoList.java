package com.teamfighttatic.HUS_OOP_ToDoApplication;

import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Ibase;

import java.util.ArrayList;
import java.util.List;

public class TodoList extends Ibase {
    private String name;
    private List<Tag> tag;
    private Boolean isComplete;
    private Boolean isStar;
    private List<List_in_TodoList> list;
    private String _id;
    public String getName() {
        return name;
    }

    public List<Tag> getTag() {
        return tag;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public void setStar(Boolean star) {
        isStar = star;
    }

    public void setList(List<List_in_TodoList> list) {
        this.list = list;
    }

    public Boolean getStar() {
        return isStar;
    }

    public List<List_in_TodoList> getList() {
        return list;
    }

    public String get_id() {
        return _id;
    }

    public TodoList(String name, List<Tag> tag, Boolean isComplete, Boolean isStar, List<List_in_TodoList> list) {
        this.name = name;
        this.tag = tag;
        this.isComplete = isComplete;
        this.isStar = isStar;
        this.list = list;
    }

}
