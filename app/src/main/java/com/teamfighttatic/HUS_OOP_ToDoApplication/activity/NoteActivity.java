package com.teamfighttatic.HUS_OOP_ToDoApplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.teamfighttatic.HUS_OOP_ToDoApplication.adapter.NoteAdapter;
import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Service;

import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {
    public static int index;
    private Button buttonAddNote;
    private Button buttonReset;
    public static ArrayList<Note> arrayNote;
    private NoteAdapter noteAdapter;
    private EditText editTextNote;
    private ListView listViewNote;
    private EditText searchNote;
    private Note note;
    Tasks tasks;
    private List<Note> listNote;
    Service service = new Service();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Gson gson = new Gson();

        buttonAddNote = findViewById(R.id.btn_addNote);
        buttonReset = findViewById(R.id.btn_reset);
        editTextNote = findViewById(R.id.edt_addNote);
        listViewNote = findViewById(R.id.lv_listNote);
        searchNote = findViewById(R.id.search);
        arrayNote = new ArrayList<>();

        noteAdapter = new NoteAdapter(NoteActivity.this, arrayNote);

        listViewNote.setTextFilterEnabled(true);
        buttonAddNote.setOnClickListener(new OnClickListener());
        buttonReset.setOnClickListener(new OnClickReset());
        listViewNote.setOnItemClickListener(new OnItemClickListener());
        tasks = gson.fromJson(getIntent().getStringExtra("taskJson"), Tasks.class);
        if (tasks == null) {
            service.getAll("note", new Service.getCallBack() {
                @Override
                public void getAllTasks(List<Tasks> tasks) {

                }

                @Override
                public void getAllNote(List<Note> note) {
                    for (int i = 0; i < note.size(); i++) {
                        arrayNote.add(i, note.get(i));
                        listViewNote.setAdapter(noteAdapter);
                    }
                }

                @Override
                public void getAllTodoList(List<TodoList> todoLists) {

                }

                @Override
                public void getAllTag(List<Tag> tags) {

                }
            });
        } else {
            listNote = tasks.getNote();
            for (int i = 0; i < listNote.size(); i++) {
                arrayNote.add(i, listNote.get(i));
                listViewNote.setAdapter(noteAdapter);
            }
        }

        searchNote.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                noteAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public class OnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String text = editTextNote.getText().toString();
            if (text.isEmpty()) {
                Toast.makeText(NoteActivity.this, "Please enter something", Toast.LENGTH_SHORT).show();
                return;
            } else {
                arrayNote.add(new Note(text, new ArrayList<Tag>(), false, false));
                noteAdapter.notifyDataSetChanged();
                if (tasks == null) {
                    service.create(new Note(text, new ArrayList<Tag>(), false, false));
                } else {
                    listNote.add(new Note(text, new ArrayList<Tag>(), false, false));
                    tasks.setNote(listNote);
                    service.update(tasks.get_id(), tasks);
                }
            }
        }
    }

    private class OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            index = position;
            Intent intent = new Intent(NoteActivity.this, NoteDetailActivity.class);
            Gson gson2 = new Gson();
            String myGson = gson2.toJson(tasks);
            intent.putExtra("taskJson", myGson);
            startActivity(intent);
        }
    }

    private class OnClickReset implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(NoteActivity.this, NoteActivity.class);
            if (tasks == null) {
                startActivity(intent);
            } else {
                Gson gson2 = new Gson();
                String myGson = gson2.toJson(tasks);
                intent.putExtra("taskJson", myGson);
                startActivity(intent);
            }
        }
    }
}
