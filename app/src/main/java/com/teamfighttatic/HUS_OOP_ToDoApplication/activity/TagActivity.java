package com.teamfighttatic.HUS_OOP_ToDoApplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.teamfighttatic.HUS_OOP_ToDoApplication.adapter.TagAdapter;
import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Service;

import java.util.ArrayList;
import java.util.List;

public class TagActivity extends AppCompatActivity {
    Tag tag;
    Button buttonAdd, buttonUpdate;
    ListView listView;
    TagAdapter adapter;
    ArrayList<Tag> arrayList;
    EditText editText;
    Service service = new Service();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        listView = findViewById(R.id.lv_listTag);
        buttonAdd = findViewById(R.id.bt_addTag);
        buttonUpdate = findViewById(R.id.bt_updateTag);
        editText = findViewById(R.id.edt_addTag);

        arrayList = new ArrayList<>();

        adapter = new TagAdapter(TagActivity.this, arrayList);
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
                    arrayList.add(i, tags.get(i));
                }
                listView.setAdapter(adapter);
            }
        });
        buttonAdd.setOnClickListener(new OnClickListener());
        listView.setOnItemClickListener(new OnItemClickListener());
        listView.setOnItemLongClickListener(new ItemLongClickRemove());

    }

    private class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String text = editText.getText().toString();
            int check = 0;
            for (int i = 0; i < arrayList.size(); i++) {
                if (text.equals(arrayList.get(i).getName())) {
                    Toast.makeText(TagActivity.this, "TAG already exist!", Toast.LENGTH_SHORT).show();
                    check++;
                    break;
                }
            }
            if (check == 0) {
                if (text.isEmpty()) {
                    Toast.makeText(TagActivity.this, "Please enter something", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    tag = new Tag(text);
                    service.create(tag);
                    startActivity(getIntent());
                }
            }

        }
    }

    private class OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            Intent intent = new Intent(TagActivity.this, FilterTag.class);
            Gson gson2 = new Gson();
//            String myGson = gson2.toJson(arrayList.get(position));
//            intent.putExtra("filter", myGson);
//            startActivity(intent);
// Comment bên trên là filter theo tag, nếu bỏ comment thầy có thể chạy tính năng lọc tasks, todoList, note theo từng tag
// Nhưng khi bỏ comment thầy phải comment lại đoạn code phía dưới nhé <3
            String text = arrayList.get(position).getName();
            editText.setText(text);
            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text2 = editText.getText().toString();
                    int check = 0;
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (text2.equals(arrayList.get(i).getName())) {
                            Toast.makeText(TagActivity.this, "TAG already exist!", Toast.LENGTH_SHORT).show();
                            check++;
                            break;
                        }
                    }
                    if(check==0){
                        if (text2.isEmpty()) {
                            Toast.makeText(TagActivity.this, "Please enter something", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Tag tagUpdate = new Tag(text2);
                            service.update(arrayList.get(position).get_id(), tagUpdate);
                            arrayList.remove(position);
                            arrayList.add(position, tagUpdate);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });

            // comment tới đây nha thầy
        }
    }

    private class ItemLongClickRemove implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TagActivity.this);
            alertDialogBuilder.setMessage("Are you sure to delete this tag?");
            alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    service.delete(arrayList.get(position).get_id(), "tag");
                    arrayList.remove(position);
                    adapter.notifyDataSetChanged();
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