package com.teamfighttatic.HUS_OOP_ToDoApplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Filter;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Note;
import com.teamfighttatic.HUS_OOP_ToDoApplication.R;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tag;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tasks;
import com.teamfighttatic.HUS_OOP_ToDoApplication.TodoList;
import com.teamfighttatic.HUS_OOP_ToDoApplication.adapter.NoteAdapter;
import com.teamfighttatic.HUS_OOP_ToDoApplication.adapter.TaskAdapter;
import com.teamfighttatic.HUS_OOP_ToDoApplication.adapter.TodoAdapter;
import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Service;

import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilterTag extends AppCompatActivity {
    Tag tag;
    Service service;
    ArrayList<Filter> filterList;
    ArrayList<Filter> arrayListFilter;
    ListView listViewTasks;
    ArrayList<Tasks> tasks;
    ListView listViewNote;
    ArrayList<Note> notes;
    ListView listViewTodo;
    ArrayList<TodoList> todoLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_tag2);
        service = new Service();
        Gson gson = new Gson();
        tag = gson.fromJson(getIntent().getStringExtra("filter"), Tag.class);
        filterList = new ArrayList<Filter>();
        tasks = new ArrayList<>();
        listViewTasks = findViewById(R.id.tasksList);
        listViewNote = findViewById(R.id.noteList);
        listViewTodo = findViewById(R.id.todoList);
        todoLists = new ArrayList<>();
        notes = new ArrayList<>();
        service.getListFilter(tag.get_id(), new Service.getFilterCallBack() {
            @Override
            public void filterCallBack(List<Filter> listFilter) {
                for(int i = 0; i < listFilter.get(0).getTasks().size(); i ++){
                    tasks.add(listFilter.get(0).getTasks().get(i));
                    TaskAdapter taskAdapter = new TaskAdapter(tasks, getApplicationContext(), R.layout.item_custom_task_view);
                    listViewTasks.setAdapter(taskAdapter);
                }
            }
        });

        service.getListFilter(tag.get_id(), new Service.getFilterCallBack() {
            @Override
            public void filterCallBack(List<Filter> listFilter) {

                if(listFilter.get(0).getTodoLists() != null){
                    for (final TodoList todoList : listFilter.get(0).getTodoLists()){
                        todoLists.add(todoList);
                        TodoAdapter todoAdapter = new TodoAdapter(getApplicationContext(), todoLists, R.layout.item_custom_todo_view);
                        listViewTodo.setAdapter(todoAdapter);
                    }
                }else{

                }
            }
        });

        service.getListFilter(tag.get_id(), new Service.getFilterCallBack() {
            @Override
            public void filterCallBack(List<Filter> listFilter) {
                for (final Note note : listFilter.get(0).getNote()){
                    notes.add(note);
                    NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(), notes);
                    listViewNote.setAdapter(noteAdapter);
                }
            }
        });

    }
}