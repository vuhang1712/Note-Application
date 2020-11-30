package com.teamfighttatic.HUS_OOP_ToDoApplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.teamfighttatic.HUS_OOP_ToDoApplication.List_in_TodoList;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Note;
import com.teamfighttatic.HUS_OOP_ToDoApplication.R;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tag;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tasks;
import com.teamfighttatic.HUS_OOP_ToDoApplication.TodoList;
import com.teamfighttatic.HUS_OOP_ToDoApplication.adapter.TodoAdapter;
import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Service;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private EditText nameTodoEditText;
    private Button addTodoButton;
    private Button buttonAlarm;
    Tasks tasks;
    Service service;
    TodoAdapter todoAdapter;
    ArrayList<TodoList> todoLists;
    ArrayList<TodoList> todoListsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        service = new Service();
        swipeRefreshLayout = findViewById(R.id.swipe_container);
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
        nameTodoEditText = findViewById(R.id.addTodoEditText);
        addTodoButton = findViewById(R.id.buttonAddTodo);
        buttonAlarm = findViewById(R.id.btn_alarmToDo);
        Gson gson = new Gson();
        tasks = gson.fromJson(getIntent().getStringExtra("taskJson"), Tasks.class);
        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddTodo(tasks, service);
            }
        });
        if(tasks!= null){
            todoLists = new ArrayList<>();
            listView = findViewById(R.id.idTodoListView);
            for(int i = 0; i < tasks.getTodoList().size(); i++){
                listView.setOnItemClickListener(new OnItemClickListener());
                todoAdapter = new TodoAdapter(getApplicationContext(),todoLists, R.layout.item_custom_todo_view, tasks);
                todoLists.add(tasks.getTodoList().get(i));
                listView.setAdapter(todoAdapter);
            }
        }else{
            todoListsAdapter = new ArrayList<>();
            listView = findViewById(R.id.idTodoListView);
            service.getAll("todoList", new Service.getCallBack() {
                @Override
                public void getAllTasks(List<Tasks> tasks) {

                }

                @Override
                public void getAllNote(List<Note> note) {

                }

                @Override
                public void getAllTodoList(List<TodoList> todoLists) {
                    for(int i = 0; i < todoLists.size(); i++){
                        listView.setOnItemClickListener(new OnItemClickListenerTodo());
                        todoAdapter = new TodoAdapter(getApplicationContext(),todoListsAdapter, R.layout.item_custom_todo_view);
                        todoListsAdapter.add(todoLists.get(i));
                        listView.setAdapter(todoAdapter);
                    }
                }

                @Override
                public void getAllTag(List<Tag> tags) {

                }
            });
        }
        buttonAlarm.setOnClickListener(new OnItemClickAlarm());

    }
    private void handleAddTodo(Tasks tasks, Service service){
        String nameTodo = nameTodoEditText.getText().toString();
        if (nameTodo.isEmpty()) {
            Toast.makeText(TodoActivity.this, "Please enter something", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if(tasks != null){
                List<List_in_TodoList> listIntTodoLists = new ArrayList<>();
                List<Tag> tag = new ArrayList<>();
                List<TodoList> todoList = tasks.getTodoList();
                TodoList todoListDetail = new TodoList(nameTodo,tag,  false, false, listIntTodoLists);
                todoList.add(todoListDetail);
                tasks.setTodoList(todoList);
                todoLists.add(todoListDetail);
                todoAdapter = new TodoAdapter(getApplicationContext(),todoLists, R.layout.item_custom_todo_view, tasks);
                listView.setAdapter(todoAdapter);
                service.update(tasks.get_id(), tasks);
            }else{
                List<List_in_TodoList> listIntTodoLists = new ArrayList<List_in_TodoList>();
                List<Tag> tag = new ArrayList<Tag>();
                TodoList todoList = new TodoList(nameTodo, tag, false, false, listIntTodoLists);
                todoListsAdapter.add(todoList);
                todoAdapter = new TodoAdapter(getApplicationContext(),todoListsAdapter, R.layout.item_custom_todo_view);
                listView.setAdapter(todoAdapter);
                service.create(todoList);
            }
        }

    }
    private class OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent DetailTaskIntent = new Intent(getApplicationContext(), ListIntTodoList.class);
            Gson gson = new Gson();
            String myGson = gson.toJson(tasks);
            String myGson2 = gson.toJson(tasks.getTodoList().get(position));
            DetailTaskIntent.putExtra("taskJson", myGson);
            DetailTaskIntent.putExtra("todoJson", myGson2);
            DetailTaskIntent.putExtra("position", position);
            startActivity(DetailTaskIntent);
        }
    }

    private class OnItemClickListenerTodo implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent DetailTaskIntentTodo = new Intent(getApplicationContext(), ListIntTodoList.class);
            Gson gsonTodo = new Gson();
            String myGsonTodo = gsonTodo.toJson(todoListsAdapter.get(position));
            DetailTaskIntentTodo.putExtra("todoJson", myGsonTodo);
            DetailTaskIntentTodo.putExtra("position", position);
            startActivity(DetailTaskIntentTodo);
        }
    }

    private class OnItemClickAlarm implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(TodoActivity.this, AlarmActivity.class);
            startActivity(intent);
        }
    }
}
