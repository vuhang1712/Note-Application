package com.teamfighttatic.HUS_OOP_ToDoApplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.teamfighttatic.HUS_OOP_ToDoApplication.R;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tasks;
import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Service;

public class AddTaskActivity extends AppCompatActivity {
    EditText editTaskText;
    Button buttonAddTask;
    CheckBox important;
    CheckBox repeat;
    Tasks tasks;
    Service service;
    Tag tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        service = new Service();
        editTaskText = findViewById(R.id.addTaskText);
        buttonAddTask = findViewById(R.id.addTaskButton);
        important = findViewById(R.id.important);
        repeat = findViewById(R.id.repeat);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddTask();
            }
        });
    }
    private void handleAddTask(){
        String text = editTaskText.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(AddTaskActivity.this, "Please enter something", Toast.LENGTH_SHORT).show();
            return;
        }else{
            service.create(new Tasks(
                    editTaskText.getText().toString(),
                    "default",
                    important.isChecked(),
                    repeat.isChecked(),
                    false
            ));
            Intent back = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(back);
        }
    }

}
