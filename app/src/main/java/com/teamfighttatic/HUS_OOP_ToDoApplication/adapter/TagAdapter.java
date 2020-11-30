package com.teamfighttatic.HUS_OOP_ToDoApplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.teamfighttatic.HUS_OOP_ToDoApplication.R;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tag;

import java.util.ArrayList;

public class TagAdapter extends BaseAdapter {

    Context context;
    ArrayList<Tag> arrayTag;

    public TagAdapter(Context context, ArrayList<Tag> arrayTag) {
        this.context = context;
        this.arrayTag = arrayTag;
    }

    @Override
    public int getCount() {
        return arrayTag.size();
    }

    @Override
    public Tag getItem(int position) {
        return arrayTag.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        Tag tag = getItem(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.tag, parent, false);
            viewHolder.textView = convertView.findViewById(R.id.tv_addTag);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(tag.getName());

        return convertView;
    }

    private class ViewHolder {
        TextView textView;
    }

}

