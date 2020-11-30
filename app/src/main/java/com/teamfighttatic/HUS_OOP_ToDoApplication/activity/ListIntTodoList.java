package com.teamfighttatic.HUS_OOP_ToDoApplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.teamfighttatic.HUS_OOP_ToDoApplication.List_in_TodoList;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Note;
import com.teamfighttatic.HUS_OOP_ToDoApplication.R;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tag;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tasks;
import com.teamfighttatic.HUS_OOP_ToDoApplication.TodoList;
import com.teamfighttatic.HUS_OOP_ToDoApplication.adapter.TagAdapter;
import com.teamfighttatic.HUS_OOP_ToDoApplication.adapter.TodoInTodoListAdapter;
import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Service;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListIntTodoList extends AppCompatActivity {
    private EditText editText;
    private Button deleteButton, updateSecify,addWork, addTodoTag;
    Service service;
    private Button addTodoInTodoList;
    private EditText addTodoInTodoListText;
    private TodoInTodoListAdapter todoInTodoListAdapter;
    private TodoList todoList;
    private Tasks tasks;
    private Gson gson;
    private ListView listViewChild, listTagInTodo;
    private ArrayList<List_in_TodoList> list_in_todoLists;
    private int positionTodoList;
    private ImageButton starButtonTodo;
    private List<TodoList> todoLists;
    private List<Tag> listTag;
    private ArrayList<Tag> arrayListTag;
    private TagAdapter tagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_int_todo_list);
        service = new Service();
        gson = new Gson();
        todoList = gson.fromJson(getIntent().getStringExtra("todoJson"), TodoList.class);
        tasks = gson.fromJson(getIntent().getStringExtra("taskJson"), Tasks.class);
        if(tasks !=null){
            todoLists = tasks.getTodoList();
        }else{

        }
        listViewChild = findViewById(R.id.id_todointodoList);

        addTodoTag = findViewById(R.id.addTodoTag);
        listTagInTodo = findViewById(R.id.listTagInTodo);
        listTagInTodo.setOnItemLongClickListener(new OnClickItemTag());
        listTag = todoList.getTag();
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
                    if(tag != null){
                        arrayListTag.add(tag);
                        tagAdapter = new TagAdapter(getApplicationContext(), arrayListTag);
                        listTagInTodo.setAdapter(tagAdapter);
                    }
                }
            });
        }
        addTodoTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddTag();
            }
        });
        positionTodoList = getIntent().getIntExtra("position", 0);
        editText = findViewById(R.id.editTodo);
        deleteButton = findViewById(R.id.deleteTodoList);
        updateSecify = findViewById(R.id.updateSpecifyTodo);
        addTodoInTodoList = findViewById(R.id.addTodoInTodoList);
        addTodoInTodoListText = findViewById(R.id.addTodoInTodoListText);
        addWork = findViewById(R.id.addWork);
        addTodoInTodoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodoInTodoList.setVisibility(View.GONE);
                addTodoInTodoListText.setVisibility(View.VISIBLE);
                addWork.setVisibility(View.VISIBLE);
            }
        });
        updateSecify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSecifyTodo();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDeleteTodoList(todoList, tasks);
            }
        });
        editText.setText(todoList.getName());
        list_in_todoLists = new ArrayList<>();
        if(todoList.getList() == null){
            System.out.println("null list in todo list");
        }else{
            for(int j  = 0; j < todoList.getList().size(); j++){
                todoInTodoListAdapter = new TodoInTodoListAdapter(getApplicationContext(),R.layout.item_todo_todolist, list_in_todoLists);
                list_in_todoLists.add(todoList.getList().get(j));
                listViewChild.setAdapter(todoInTodoListAdapter);

            }
        }
        listViewChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListIntTodoList.this);
                alertDialogBuilder.setMessage("Complete ?");
                alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<List_in_TodoList> listIntTodoLists = todoList.getList();
                        listIntTodoLists.remove(position);
                        todoList.setList(listIntTodoLists);
                        if(tasks != null){
                            todoLists.set(positionTodoList, todoList);
                            tasks.setTodoList(todoLists);
                            service.update(tasks.get_id(), tasks);
                        }else{
                            service.update(todoList.get_id(), todoList);
                        }
                        list_in_todoLists.remove(position);
                        todoInTodoListAdapter = new TodoInTodoListAdapter(getApplicationContext(),R.layout.item_todo_todolist, list_in_todoLists);
                        listViewChild.setAdapter(todoInTodoListAdapter);
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialogBuilder.show();
            }
        });
        addWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddWord();
            }
        });

    }
    private void updateSecifyTodo(){
        String editTodo = editText.getText().toString();
        if(editTodo.isEmpty()){
            Toast.makeText(ListIntTodoList.this, "Please enter something", Toast.LENGTH_SHORT).show();
            return;
        }else{
            if(tasks == null){
                todoList.setName(editTodo);
                service.update(todoList.get_id(), todoList);
                Intent intent = new Intent(ListIntTodoList.this, TodoActivity.class);
//                Gson gson2 = new Gson();
//                String myGson = gson2.toJson(tasks);
//                intent.putExtra("taskJson", myGson);
                startActivity(intent);
            }else{
                todoList.setName(editTodo);
                List<TodoList> todoLists = tasks.getTodoList();
                todoLists.set(positionTodoList, todoList);
                tasks.setTodoList(todoLists);
                service.update(tasks.get_id(), tasks);
                Intent intent = new Intent(ListIntTodoList.this, TodoActivity.class);
//                Gson gson2 = new Gson();
//                String myGson = gson2.toJson(tasks);
//                intent.putExtra("taskJson", myGson);
                String myGson = gson.toJson(tasks);
                String myGson2 = gson.toJson(tasks.getTodoList().get(positionTodoList));
                intent.putExtra("taskJson", myGson);
                intent.putExtra("todoJson", myGson2);
                intent.putExtra("position", positionTodoList);
                startActivity(intent);
            }
        }
    }
    private void handleAddTag(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog_add_tag, null);
        final ListView listViewTag = alertLayout.findViewById(R.id.lv_tag);
        final ArrayList<Tag> list = new ArrayList<>();
        final TagAdapter adapterTag = new TagAdapter(ListIntTodoList.this, list);
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
                ArrayList<Boolean> checkExist = new ArrayList<>();
                for(int i = 0 ; i < arrayListTag.size(); i++){
                    checkExist.add(arrayListTag.get(i).get_id().equals(list.get(position).get_id()));
                }
                if(checkExist.contains(true)){
                    Toast.makeText(ListIntTodoList.this, "Tag đã tồn tại", Toast.LENGTH_SHORT).show(); // k them
                }else{
                    Toast.makeText(ListIntTodoList.this, "Thêm Tag thành công", Toast.LENGTH_SHORT).show();
                    arrayListTag.add(list.get(position));
                }
            }
        });
        AlertDialog.Builder alert = new AlertDialog.Builder(ListIntTodoList.this);
        alert.setTitle("Add Tag in this Tasks");
        alert.setView(alertLayout);
        alert.setCancelable(false);

        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tagAdapter = new TagAdapter(getApplicationContext(), arrayListTag);
                listTagInTodo.setAdapter(tagAdapter);
                if(tasks != null){
                    todoLists.get(positionTodoList).setTag(arrayListTag);
                    tasks.setTodoList(todoLists);
                    service.update(tasks.get_id(), tasks);
                }else{
                    todoList.setTag(arrayListTag);
                    service.update(todoList.get_id(), todoList);
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ListIntTodoList.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }
    private class OnClickItemTag implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListIntTodoList.this);
            alertDialogBuilder.setMessage("Are you sure to delete this tag?");
            alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    arrayListTag.remove(position);
                    listTag.remove(position);
                    tagAdapter = new TagAdapter(getApplicationContext(), arrayListTag);
                    listTagInTodo.setAdapter(tagAdapter);
                    tagAdapter.notifyDataSetChanged();
                    if(tasks != null){
                        todoLists.get(positionTodoList).setTag(arrayListTag);
                        tasks.setTodoList(todoLists);
                        service.update(tasks.get_id(), tasks);
                    }else{
                        todoList.setTag(arrayListTag);
                        service.update(todoList.get_id(), todoList);
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
    private void handleAddWord(){
        String nameTodoList = addTodoInTodoListText.getText().toString();
        if (nameTodoList.isEmpty()) {
            Toast.makeText(ListIntTodoList.this, "Please enter something", Toast.LENGTH_SHORT).show();
            return;
        }else{
            List<List_in_TodoList> listIntTodoLists = todoList.getList();
            List_in_TodoList list_in_todoList = new List_in_TodoList(nameTodoList, false);
            listIntTodoLists.add(list_in_todoList);
            list_in_todoLists.add(list_in_todoList);
            listViewChild.setAdapter(todoInTodoListAdapter);
            todoList.setList(listIntTodoLists);
            if(tasks != null){
                List<TodoList> todoLists = tasks.getTodoList();
                todoLists.set(positionTodoList, todoList);
                tasks.setTodoList(todoLists);
                service.update(tasks.get_id(), tasks);
            }else{
                service.update(todoList.get_id(), todoList);
            }
        }
    }
    private void handleDeleteTodoList(final TodoList todoList, final Tasks tasks){
        final com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Service service = new com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Service();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListIntTodoList.this);
        alertDialogBuilder.setMessage("Are you sure to delete this TodoList?");
        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(tasks != null){
                    List<TodoList> todoLists = tasks.getTodoList();
                    for(int i = 0 ; i < todoLists.size(); i++){
                        if(todoLists.get(i).getName().equals(todoList.getName())){
                            todoLists.remove(i);
                        }else{
                            System.out.println("k co");
                        }
                    }
                    tasks.setTodoList(todoLists);
                    service.update(tasks.get_id(), tasks);
                }else{
                    service.delete(todoList.get_id(), "todoList");
                }
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
}
