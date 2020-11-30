package com.teamfighttatic.HUS_OOP_ToDoApplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Ibase;
import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Service;

import java.util.ArrayList;
import java.util.List;

public class Tasks extends Ibase {
    @SerializedName("nameTask")
    private String nameTask;
    @SerializedName("dateExpiration")
    private String dateExpiration;
    @SerializedName("star")
    private Boolean star;
    @SerializedName("repeat")
    private Boolean repeat;
    @SerializedName("tag")
    private List<Tag> tag;
    @SerializedName("todoList")
    private List<TodoList> todoList;
    @SerializedName("note")
    private List<Note> note;
    @SerializedName("isComplete")
    private Boolean isComplete;

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    private String _id;

    public Tasks(String nameTask, String dateExpiration, Boolean star, Boolean repeat, Boolean isComplete) {
        this.nameTask = nameTask;
        this.dateExpiration = dateExpiration;
        this.star = star;
        this.repeat = repeat;
        this.isComplete = isComplete;
    }

    public Tasks(
            String nameTask,
            String dateExpiration,
            Boolean star,
            Boolean repeat,
            List<Tag> tag,
            List<TodoList> todoList,
            List<Note> note
            ) {
        this.nameTask = nameTask;
        this.dateExpiration = dateExpiration;
        this.star = star;
        this.repeat = repeat;
        this.tag = tag;
        this.todoList = todoList;
        this.note = note;
    }

    public void setTodoList(List<TodoList> todoList) {
        this.todoList = todoList;
    }

    public List<TodoList> getTodoList() {
        return todoList;
    }

    public List<Note> getNote() {
        return note;
    }

    public List<Tag> getTag() {
        return tag;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public void setStar(Boolean star) {
        this.star = star;
    }

    public void setRepeat(Boolean repeat) {
        this.repeat = repeat;
    }

    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }

    public void setNote(List<Note> note) {
        this.note = note;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public Boolean getStar() {
        return star;
    }

    public Boolean getRepeat() {
        return repeat;
    }

    public String get_id() {
        return _id;
    }

    public static void main(String[] args) {

    }
    public static void getAllTasks(){
        Service service = new Service();
        service.getAll("tasks", new Service.getCallBack() {
            @Override
            public void getAllTasks(List<Tasks> tasks) {
                for(int i = 0; i< tasks.size(); i++){
                    listIbase.add(tasks.get(i));
                }
//                Tasks.findIbase();
            }

            @Override
            public void getAllNote(List<Note> note) {

            }

            @Override
            public void getAllTodoList(List<TodoList> todoLists) {

            }

            @Override
            public void getAllTag(List<Tag> tags) {

            }
        });
    }
}
