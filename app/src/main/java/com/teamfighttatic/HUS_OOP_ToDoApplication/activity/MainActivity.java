package com.teamfighttatic.HUS_OOP_ToDoApplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.teamfighttatic.HUS_OOP_ToDoApplication.Note;
import com.teamfighttatic.HUS_OOP_ToDoApplication.R;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tag;
import com.teamfighttatic.HUS_OOP_ToDoApplication.TodoList;
import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Service;
import com.teamfighttatic.HUS_OOP_ToDoApplication.adapter.TaskAdapter;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tasks;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Tasks> getAllTasks;
    private ListView listView;
    private Button buttonTask;
    private Button buttonNote;
    private Button buttonTag;
    private Button todoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipe_container_main);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "Works!", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        // Stop animation (This will be after 3 secondsx)
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        Service service = new Service();
        listView =  findViewById(R.id.idListView);
        buttonTask = findViewById(R.id.addTask);
        buttonNote = findViewById(R.id.addNote);
        buttonTag = findViewById(R.id.addTag);
        todoList = findViewById(R.id.todoList);
        initView(service);
        buttonTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddTask();
            }
        });
        buttonNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddNote();
            }
        });
        buttonTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddTag();
            }
        });
        todoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnclickTodoView();
            }
        });
    }
    private void setOnclickTodoView(){
        Intent todoActivity = new Intent(getApplicationContext(), TodoActivity.class);
        startActivity(todoActivity);
    }
    private void handleAddTag(){
        Intent addTag = new Intent(this, TagActivity.class);
        startActivity(addTag);
    }
    private void handleAddTask(){
        Intent addTask = new Intent(this, AddTaskActivity.class);
        startActivity(addTask);
    }

    private void handleAddNote() {
        Intent addNote = new Intent(this, NoteActivity.class);
        startActivity(addNote);
    }
    public void initView(Service service){
        service.getAll("tasks", new Service.getCallBack() {
            ArrayList<Tasks> arrayListTask = new ArrayList<>();
            @Override
            public void getAllTasks(List<Tasks> tasks) {
                for(final Tasks getAllTasks : tasks){
                    arrayListTask.add(getAllTasks);
                    TaskAdapter taskAdapter = new TaskAdapter(arrayListTask, getApplicationContext(), R.layout.item_custom_task_view);
                    listView.setAdapter(taskAdapter);
                }
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
