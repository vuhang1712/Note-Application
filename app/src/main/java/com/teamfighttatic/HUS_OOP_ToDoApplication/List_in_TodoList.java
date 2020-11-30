package com.teamfighttatic.HUS_OOP_ToDoApplication;

import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Ibase;

public class List_in_TodoList extends Ibase {
    private String name;
    private Boolean isComplete;

    public void setName(String name) {
        this.name = name;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public String getName() {
        return name;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public List_in_TodoList(String name, Boolean isComplete) {
        this.name = name;
        this.isComplete = isComplete;
    }
    public List_in_TodoList() {
    }
}
