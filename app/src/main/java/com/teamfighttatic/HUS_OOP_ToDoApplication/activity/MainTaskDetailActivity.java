package com.teamfighttatic.HUS_OOP_ToDoApplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Note;
import com.teamfighttatic.HUS_OOP_ToDoApplication.R;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tag;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tasks;
import com.teamfighttatic.HUS_OOP_ToDoApplication.TodoList;
import com.teamfighttatic.HUS_OOP_ToDoApplication.adapter.TagAdapter;
import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Service;

import java.util.ArrayList;
import java.util.List;

public class MainTaskDetailActivity extends AppCompatActivity {
    private EditText editText;
    private CheckBox specifyImportant;
    private CheckBox specifyRepeat;
    private Button buttonNote;
    private Button buttonTodo;
    private Button deleteTask;
    private Button alarmTask;
    private Button updateSpecifyTask, addTaskTag;
    private Service service;
    private ListView listView;
    private List<Tag> listTag;
    private TagAdapter tagAdapter;
    private Tasks tasks;
    private ArrayList<Tag> arrayListTag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task_detail);
        listView = findViewById(R.id.listTagInTasks);
        listView.setOnItemLongClickListener(new OnClickItemTag());
        service = new Service();
        Gson gson = new Gson();
        tasks = gson.fromJson(getIntent().getStringExtra("taskJson"), Tasks.class);
        listTag = tasks.getTag();
        arrayListTag = new ArrayList<>();
        for(int i = 0; i < listTag.size(); i++){
                service.getSpecify("tag", listTag.get(i).get_id(), new Service.getSpecifyCallBack() {
                    @Override
                    public void getTasks(Tasks tasks) {

                    }

                    @Override
                    public void getNote(Note note) {

                    }

                    @Override
                    public void getTodoList(TodoList todoList) {

                    }

                    @Override
                    public void getTag(Tag tag) {
                        if(tag == null){
                            // delete tag thừa
                        }else{
                            arrayListTag.add(tag);
                            tagAdapter = new TagAdapter(getApplicationContext(), arrayListTag);
                            listView.setAdapter(tagAdapter);
                        }
                    }
                });

        }

        editText = findViewById(R.id.addSpecifyTaskText);
        specifyImportant = findViewById(R.id.specifyImportant);
        specifyRepeat = findViewById(R.id.specifyRepeat);
        addTaskTag = findViewById(R.id.addTaskTag);
        addTaskTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickAddTag();
            }
        });
        buttonNote = findViewById(R.id.viewNote);
        buttonTodo = findViewById(R.id.viewTodoList);
        deleteTask = findViewById(R.id.deleteTask);
        alarmTask = findViewById(R.id.btn_alarmTask);
        updateSpecifyTask = findViewById(R.id.updateSpecifyTask);
        updateSpecifyTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hanldeUpdateSpecifyTask(tasks);
            }
        });
        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDeleteTask(tasks);
            }
        });
        buttonTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleMoveTodo(tasks);
            }
        });
        buttonNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleMoveNote(tasks);
            }
        });
        editText.setText(tasks.getNameTask());
        if(tasks.getStar()){
            specifyImportant.setChecked(true);
        }else{
            specifyImportant.setChecked(false);
        }
        if(tasks.getRepeat()){
            specifyRepeat.setChecked(true);
        }else{
            specifyRepeat.setChecked(false);
        }
        alarmTask.setOnClickListener(new OnClickAlarm());
    }

    public void OnClickAddTag(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog_add_tag, null);
        final ListView listViewTag = alertLayout.findViewById(R.id.lv_tag);
        final ArrayList<Tag> list = new ArrayList<>();
        final TagAdapter adapterTag = new TagAdapter(MainTaskDetailActivity.this, list);
        service.getAll("tag", new Service.getCallBack() {
            @Override
            public void getAllTasks(List<Tasks> tasks) {

            }

            @Override
            public void getAllNote(List<Note> note) {

            }

            @Override
            public void getAllTodoList(List<TodoList> todoLists) {

            }

            @Override
            public void getAllTag(List<Tag> tags) {
                for (int i = 0; i < tags.size(); i++) {
                    list.add(i, tags.get(i));
                    listViewTag.setAdapter(adapterTag);
                }
            }
        });
        listViewTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainTaskDetailActivity.this, "Added", Toast.LENGTH_SHORT).show();
                ArrayList<Boolean> checkExist = new ArrayList<>();
                for(int i = 0 ; i < arrayListTag.size(); i++){
                    checkExist.add(arrayListTag.get(i).get_id().equals(list.get(position).get_id()));
                }
                if(checkExist.contains(true)){
                    Toast.makeText(MainTaskDetailActivity.this, "Tag đã tồn tại", Toast.LENGTH_SHORT).show(); // k them
                }else{
                    Toast.makeText(MainTaskDetailActivity.this, "Thêm Tag thành công", Toast.LENGTH_SHORT).show();
                    arrayListTag.add(list.get(position));
                }
            }
        });
        AlertDialog.Builder alert = new AlertDialog.Builder(MainTaskDetailActivity.this);
        alert.setTitle("Add Tag in this Tasks");
        alert.setView(alertLayout);
        alert.setCancelable(false);

        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tagAdapter = new TagAdapter(getApplicationContext(), arrayListTag);
                listView.setAdapter(tagAdapter);
                tasks.setTag(arrayListTag);
                service.update(tasks.get_id(), tasks);
//                Toast.makeText(MainTaskDetailActivity.this, "You added tag", Toast.LENGTH_SHORT).show();
//                startActivity(getIntent());
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainTaskDetailActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }


    private class OnClickItemTag implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainTaskDetailActivity.this);
            alertDialogBuilder.setMessage("Are you sure to delete this tag?");
            alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    arrayListTag.remove(position);
                    listTag.remove(position);
                    tagAdapter.notifyDataSetChanged();
                    tasks.setTag(listTag);
                    service.update(tasks.get_id(),tasks);
                }
            });
            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialogBuilder.show();
            return true;
        }
    }
    private void hanldeUpdateSpecifyTask(Tasks tasks){
        tasks.setNameTask(editText.getText().toString());
        tasks.setStar(specifyImportant.isChecked());
        tasks.setRepeat(specifyRepeat.isChecked());
        service.update(tasks.get_id(), tasks);
        Intent back = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(back);
    }
    private void handleMoveNote(Tasks tasks){
        Intent i = new Intent(this, NoteActivity.class);
        Gson gson2 = new Gson();
        String myGson = gson2.toJson(tasks);
        i.putExtra("taskJson", myGson);
        startActivity(i);
    }
    private void handleMoveTodo(Tasks tasks){
        Intent i = new Intent(this, TodoActivity.class);
        Gson gson2 = new Gson();
        String myGson = gson2.toJson(tasks);
        i.putExtra("taskJson", myGson);
        startActivity(i);
    }

    private void handleDeleteTask(final Tasks task){
//        System.out.println(task.get_id());
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainTaskDetailActivity.this);
        alertDialogBuilder.setMessage("Are you sure to delete this task?");
        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                service.delete(task.get_id(), "tasks");
                Intent back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back);
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialogBuilder.show();
    }

    private class OnClickAlarm implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainTaskDetailActivity.this, AlarmActivity.class);
            startActivity(intent);
        }
    }
}
