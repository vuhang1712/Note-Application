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

import static com.teamfighttatic.HUS_OOP_ToDoApplication.activity.NoteActivity.arrayNote;
import static com.teamfighttatic.HUS_OOP_ToDoApplication.activity.NoteActivity.index;

public class NoteDetailActivity extends AppCompatActivity {
    private CheckBox checkBoxStar;
    private Button buttonAlarm;
    private Button buttonDelete;
    private Button buttonSave;
    private Button buttonAddTag;
    private EditText editText;
    private ListView listView;
    private ArrayList<Tag> listTagInNote;
    private TagAdapter adapterTagInNote;
    private Service service = new Service();
    private Tasks tasks;
    private ArrayList<Tag> listTagInNoteAPI = new ArrayList<>();

    Note note = arrayNote.get(index);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        Gson gson = new Gson();
        tasks = gson.fromJson(getIntent().getStringExtra("taskJson"), Tasks.class);

        editText = findViewById(R.id.edt_noteDetail);
        checkBoxStar = findViewById(R.id.cb_start);
        buttonAlarm = findViewById(R.id.btn_alarm);
        buttonDelete = findViewById(R.id.btn_deleteNote);
        buttonSave = findViewById(R.id.btn_saveNote);
        buttonAddTag = findViewById(R.id.btn_addTag);
        listView = findViewById(R.id.lv_listTagInNote);

        editText.setText(note.getName());
        listTagInNote = note.getTag();
        for (int i = 0; i < listTagInNote.size(); i++) {
            service.getSpecify("tag", listTagInNote.get(i).get_id(), new Service.getSpecifyCallBack() {
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
                    if (tag != null) {
                        listTagInNoteAPI.add(tag);
                        adapterTagInNote = new TagAdapter(NoteDetailActivity.this, listTagInNoteAPI);
                        listView.setAdapter(adapterTagInNote);
                    } else {
                        // do something
                    }
                }
            });
        }

        if (note.getStar() == true)
            checkBoxStar.setChecked(true);
        else {
            checkBoxStar.setChecked(false);
        }

        buttonAlarm.setOnClickListener(new OnClickAlarm());
        buttonDelete.setOnClickListener(new OnClickDelete());
        buttonSave.setOnClickListener(new OnClickSave());
        buttonAddTag.setOnClickListener(new OnClickAddTag());
        listView.setOnItemLongClickListener(new OnClickItemTag());
    }

    private class OnClickAlarm implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(NoteDetailActivity.this, AlarmActivity.class);
            startActivity(intent);
        }
    }

    private class OnClickAddTag implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.custom_dialog_add_tag, null);
            //final EditText editTextTag = alertLayout.findViewById(R.id.edt_addTagInNote);
            final ListView listViewTag = alertLayout.findViewById(R.id.lv_tag);
            final ArrayList<Tag> list = new ArrayList<>();
            final TagAdapter adapterTag = new TagAdapter(NoteDetailActivity.this, list);

            AlertDialog.Builder alert = new AlertDialog.Builder(NoteDetailActivity.this);
            alert.setTitle("Hi. I am 'tag'. Can I help you!");
            alert.setView(alertLayout);
            alert.setCancelable(false);

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
            if (tasks == null) {
                if (note.getTag() != null) {
                    listTagInNote = note.getTag();
                }
                listViewTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                        service.getSpecify("note", note.get_id(), new Service.getSpecifyCallBack() {
                            @Override
                            public void getTasks(Tasks tasks) {

                            }

                            @Override
                            public void getNote(Note note) {
                                int flag = 0;
                                for (int i = 0; i < listTagInNote.size(); i++) {
                                    if (!listTagInNote.isEmpty()) {
                                        if (list.get(position).getName().equals(listTagInNote.get(i).getName())) {
                                            Toast.makeText(NoteDetailActivity.this, "TAG already exist!", Toast.LENGTH_SHORT).show();
                                            flag++;
                                            break;
                                        }
                                    }
                                }
                                if (flag == 0) {
                                    Toast.makeText(NoteDetailActivity.this, "Added", Toast.LENGTH_SHORT).show();
                                    listTagInNote.add(list.get(position));
                                }
                            }

                            @Override
                            public void getTodoList(TodoList todoList) {

                            }

                            @Override
                            public void getTag(Tag tag) {

                            }
                        });
                    }
                });
            } else {
                listViewTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ArrayList<Boolean> checkExist = new ArrayList<>();
                        for (int i = 0; i < listTagInNoteAPI.size(); i++) {
                            checkExist.add(listTagInNoteAPI.get(i).get_id().equals(list.get(position).get_id()));
                        }
                        if (checkExist.contains(true)) {
                            Toast.makeText(NoteDetailActivity.this, "Tag đã tồn tại", Toast.LENGTH_SHORT).show(); // k them
                        } else {
                            Toast.makeText(NoteDetailActivity.this, "Thêm Tag thành công", Toast.LENGTH_SHORT).show();
                            listTagInNoteAPI.add(list.get(position));
                        }
                    }
                });
            }
            alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (tasks == null) {
                        note.setTag(listTagInNote);
                        service.update(note.get_id(), note);

                        Toast.makeText(NoteDetailActivity.this, "You added tag", Toast.LENGTH_SHORT).show();

                        startActivity(getIntent());
                    } else {
                        note.setTag(listTagInNoteAPI);
                        List<Note> listNote = tasks.getNote();
                        listNote.set(index, note);
                        adapterTagInNote = new TagAdapter(NoteDetailActivity.this, listTagInNoteAPI);
                        listView.setAdapter(adapterTagInNote);
                        tasks.setNote(listNote);
                        service.update(tasks.get_id(), tasks);
                    }
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(NoteDetailActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog dialog = alert.create();
            dialog.show();
        }
    }

    private class OnClickDelete implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NoteDetailActivity.this);
            alertDialogBuilder.setMessage("Are you sure to delete this note?");
            alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (tasks == null) {
                        service.delete(note.get_id(), "note");
                    } else {
                        List<Note> listNote = tasks.getNote();
                        listNote.remove(index);
                        tasks.setNote(listNote);
                        service.update(tasks.get_id(), tasks);
                    }
                    finish();
                }
            });
            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialogBuilder.show();

        }
    }

    private class OnClickSave implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String text2 = editText.getText().toString();

            if (text2.isEmpty()) {
                Toast.makeText(NoteDetailActivity.this, "Please enter something", Toast.LENGTH_SHORT).show();
                return;
            } else {
                note.setName(text2);
            }

            if (checkBoxStar.isChecked()) {
                note.setStar(true);
            } else {
                note.setStar(false);
            }
            if (tasks == null) {
                service.update(note.get_id(), note);
                startActivity(getIntent());
            } else {
                List<Note> listNote = tasks.getNote();
                listNote.set(index, note);
//                listNote.remove(NoteActivity.index);
                tasks.setNote(listNote);
                service.update(tasks.get_id(), tasks);
                Intent intent = new Intent(NoteDetailActivity.this, NoteActivity.class);
                Gson gson2 = new Gson();
                String myGson = gson2.toJson(tasks);
                intent.putExtra("taskJson", myGson);
                startActivity(intent);
            }
        }
    }

    private class OnClickItemTag implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NoteDetailActivity.this);
            alertDialogBuilder.setMessage("Are you sure to delete this tag?");
            alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    listTagInNote.remove(position);
                    adapterTagInNote.notifyDataSetChanged();
                    note.setTag(listTagInNote);
                    if (tasks == null) {
                        service.update(note.get_id(), note);
                    } else {
                        List<Note> listNote = tasks.getNote();
                        listNote.set(index, note);
                        tasks.setNote(listNote);
                        service.update(tasks.get_id(), tasks);
                    }
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
}