package com.teamfighttatic.HUS_OOP_ToDoApplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.teamfighttatic.HUS_OOP_ToDoApplication.List_in_TodoList;
import com.teamfighttatic.HUS_OOP_ToDoApplication.R;
import com.teamfighttatic.HUS_OOP_ToDoApplication.TodoList;
import com.teamfighttatic.HUS_OOP_ToDoApplication.activity.ListIntTodoList;

import java.util.ArrayList;

public class TodoInTodoListAdapter extends BaseAdapter {
    Context context;
    private int idLayout;
    ArrayList<List_in_TodoList> todoInTodoLists;

    public TodoInTodoListAdapter(Context context, int idLayout, ArrayList<List_in_TodoList> todoInTodoLists) {
        this.context = context;
        this.idLayout = idLayout;
        this.todoInTodoLists = todoInTodoLists;
    }

    @Override
    public int getCount() {
        return todoInTodoLists.size();
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
//        final ImageButton deleteListTodoList = convertView.findViewById(R.id.deleteListTodoList);

        final List_in_TodoList todo = todoInTodoLists.get(position);
        TextView textView = convertView.findViewById(R.id.todoIntodoListName);
        if (textView != null) {
            textView.setText(todo.getName());
        }
        return convertView;
    }
}
