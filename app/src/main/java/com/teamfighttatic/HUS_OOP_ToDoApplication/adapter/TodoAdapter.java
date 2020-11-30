package com.teamfighttatic.HUS_OOP_ToDoApplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.teamfighttatic.HUS_OOP_ToDoApplication.List_in_TodoList;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Note;
import com.teamfighttatic.HUS_OOP_ToDoApplication.R;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tag;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tasks;
import com.teamfighttatic.HUS_OOP_ToDoApplication.TodoList;
import com.teamfighttatic.HUS_OOP_ToDoApplication.activity.ListIntTodoList;
import com.teamfighttatic.HUS_OOP_ToDoApplication.activity.MainTaskDetailActivity;
import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Service;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends BaseAdapter {
    Context context;
    private int idLayout;
    private ArrayList<TodoList> todoLists;
    private ListView listView;
    private Tasks tasks;
    public TodoAdapter(Context context, ArrayList<TodoList> todoLists, int idLayout, Tasks tasks) {
        this.context = context;
        this.todoLists = todoLists;
        this.idLayout = idLayout;
        this.tasks = tasks;
    }
    public TodoAdapter(Context context, ArrayList<TodoList> todoLists, int idLayout) {
        this.context = context;
        this.todoLists = todoLists;
        this.idLayout = idLayout;
    }
    @Override
    public int getCount() {
        return todoLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(idLayout, parent, false);
        }
        final Service service = new Service();
        final TodoList todoList = todoLists.get(position);
        TextView textView = convertView.findViewById(R.id.todoName);
        final CheckBox completeCheckBox = convertView.findViewById(R.id.completeCheckBox);


        if(todoList.getComplete()){
            completeCheckBox.setChecked(true);
        }else{
            completeCheckBox.setChecked(false);
        }
        final ImageButton starButtonTodo = convertView.findViewById(R.id.starButtonTodo);
        if(todoList.getStar()){
            starButtonTodo.setImageResource(R.drawable.ic_yellowstar);
        }else{
            starButtonTodo.setImageResource(R.drawable.ic_favorites);
        }

        if(tasks == null){
            completeCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    todoList.setComplete(!todoList.getComplete());
                    if(todoList.getComplete()){
                        completeCheckBox.setChecked(true);
                    }else{
                        completeCheckBox.setChecked(false);
                    }
                    service.update(todoList.get_id(),todoList);
                }
            });
            starButtonTodo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    todoList.setStar(!todoList.getStar());
                    if(todoList.getStar()){
                        starButtonTodo.setImageResource(R.drawable.ic_yellowstar);
                    }else{
                        starButtonTodo.setImageResource(R.drawable.ic_favorites);
                    }
                    service.update(todoList.get_id(),todoList);
                }
            });
        }else{
            starButtonTodo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    todoList.setStar(!todoList.getStar());
                    todoLists.set(position, todoList);
                    if(todoList.getStar()){
                        starButtonTodo.setImageResource(R.drawable.ic_yellowstar);
                    }else{
                        starButtonTodo.setImageResource(R.drawable.ic_favorites);
                    }
                    tasks.setTodoList(todoLists);
                    service.update(tasks.get_id(),tasks);
                }
            });
            completeCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    todoList.setComplete(!todoList.getComplete());
                    if(todoList.getComplete()){
                        completeCheckBox.setChecked(true);
                    }else{
                        completeCheckBox.setChecked(false);
                    }
                    todoLists.set(position, todoList);
                    tasks.setTodoList(todoLists);
                    service.update(tasks.get_id(),tasks);
                }
            });

        }

        LinearLayout linearLayoutTag = convertView.findViewById(R.id.tagTodoListLayout);
        if (textView != null) {
            textView.setText(todoList.getName());
            if(todoList.getTag() == null){
                System.out.println("Dat");
            }else{
                for(int i =0; i< todoList.getTag().size(); i++){
                    final TextView text = new TextView(context);
                    text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    service.getSpecify("tag", todoList.getTag().get(i).get_id(), new Service.getSpecifyCallBack() {
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
                                // delete tag thừa nếu tag trả về null
                            }else{
                                text.setText(tag.getName() + ", ");
                            }
                        }
                    });
                    text.setTextSize(14);
                    linearLayoutTag.addView(text);
                }
            }
        }
        return convertView;
    }
}
