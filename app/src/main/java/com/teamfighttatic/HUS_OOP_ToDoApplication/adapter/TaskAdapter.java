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
import android.widget.TextView;

import com.google.gson.Gson;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Note;
import com.teamfighttatic.HUS_OOP_ToDoApplication.R;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tag;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tasks;
import com.teamfighttatic.HUS_OOP_ToDoApplication.TodoList;
import com.teamfighttatic.HUS_OOP_ToDoApplication.activity.MainTaskDetailActivity;
import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Service;

import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {
    Context context;
    ArrayList<Tasks> getAllTasks;
    Service service = new Service();
    private int idLayout;
    @Override
    public int getCount() {
        return getAllTasks.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    public TaskAdapter(ArrayList<Tasks> getAllTasks, Context context, int idLayout) {
        this.getAllTasks = getAllTasks;
        this.context = context;
        this.idLayout = idLayout;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(idLayout, parent, false);
        }
        final Tasks getAllTaskAdd = getAllTasks.get(position);
        TextView taskName =  convertView.findViewById(R.id.taskName);

        final CheckBox checkImportantBox = convertView.findViewById(R.id.checkImportantBox);
        if(getAllTaskAdd.getComplete()){
            checkImportantBox.setChecked(true);
        }else{
            checkImportantBox.setChecked(false);
        }

        checkImportantBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllTaskAdd.setComplete(!getAllTaskAdd.getComplete());
                if(getAllTaskAdd.getComplete()){
                    checkImportantBox.setChecked(true);
                }else{
                    checkImportantBox.setChecked(false);
                }
                service.update(getAllTaskAdd.get_id(), getAllTaskAdd);
            }
        });

        final ImageButton imageButton = convertView.findViewById(R.id.starButton);
        if(getAllTaskAdd.getStar()){
            imageButton.setImageResource(R.drawable.ic_yellowstar);
        }else{
            imageButton.setImageResource(R.drawable.ic_favorites);
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getAllTaskAdd.setStar(!getAllTaskAdd.getStar());
                    if(getAllTaskAdd.getStar()){
                        imageButton.setImageResource(R.drawable.ic_yellowstar);
                    }else{
                        imageButton.setImageResource(R.drawable.ic_favorites);
                    }
                    service.update(getAllTaskAdd.get_id(), getAllTaskAdd);
            }
        });

        LinearLayout linearLayoutTag = convertView.findViewById(R.id.tagLayout);
        if (taskName != null) {
            taskName.setText(getAllTaskAdd.getNameTask());
            for(int i =0; i< getAllTaskAdd.getTag().size(); i++){
                final TextView text = new TextView(context);
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                service.getSpecify("tag", getAllTaskAdd.getTag().get(i).get_id(), new Service.getSpecifyCallBack() {
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
        final LinearLayout linearLayout =  convertView.findViewById(R.id.idLinearLayout);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnclick(context, getAllTaskAdd);
            }
        });
        return convertView;
    }
    public void setOnclick(Context context, Tasks getAllTaskAdd){
        Intent DetailTaskIntent = new Intent(context, MainTaskDetailActivity.class);
        Gson gson = new Gson();
        String myGson = gson.toJson(getAllTaskAdd);
        DetailTaskIntent.putExtra("taskJson", myGson);
        DetailTaskIntent.setFlags(DetailTaskIntent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(DetailTaskIntent);
    }
}
