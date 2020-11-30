package com.teamfighttatic.HUS_OOP_ToDoApplication;

import java.util.List;

public class Filter {
    private List<Tasks> tasks;
    private List<Note> note;
    private List<TodoList> todoLists;

    public Filter(List<Tasks> tasks, List<Note> note, List<TodoList> todoLists) {
        this.tasks = tasks;
        this.note = note;
        this.todoLists = todoLists;
    }

    public List<Tasks> getTasks() {
        return tasks;
    }

    public List<Note> getNote() {
        return note;
    }

    public List<TodoList> getTodoLists() {
        return todoLists;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }

    public void setNote(List<Note> note) {
        this.note = note;
    }

    public void setTodoLists(List<TodoList> todoLists) {
        this.todoLists = todoLists;
    }
}
