package com.teamfighttatic.HUS_OOP_ToDoApplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamfighttatic.HUS_OOP_ToDoApplication.Note;
import com.teamfighttatic.HUS_OOP_ToDoApplication.R;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tag;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tasks;
import com.teamfighttatic.HUS_OOP_ToDoApplication.TodoList;
import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Service;

import java.util.ArrayList;

public class NoteAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<Note> arrayNote = null;
    ArrayList<Note> tempArrayNote;
    CustomFilter cs;
    public NoteAdapter(Context context, ArrayList<Note> arrayNote ) {
        this.context = context;
        this.arrayNote = arrayNote;
        this.tempArrayNote = arrayNote;
    }

    @Override
    public int getCount() {
        return arrayNote.size();
    }

    @Override
    public Note getItem(int position) {
        return arrayNote.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final NoteAdapter.ViewHolder viewHolder;
        Note note = getItem(position);
        if (convertView == null) {
            viewHolder = new NoteAdapter.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.one_note, parent, false);
            viewHolder.textView = convertView.findViewById(R.id.tv_note);
            viewHolder.imageViewStar = convertView.findViewById(R.id.cb_star);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (NoteAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(note.getName());

        if (note.getStar() == true) {
            viewHolder.imageViewStar.setVisibility(convertView.VISIBLE);
        } else {
            viewHolder.imageViewStar.setVisibility(convertView.INVISIBLE);
        }

        Service service = new Service();
        LinearLayout linearLayout = convertView.findViewById(R.id.wrapTag);
        if (note.getTag() != null) {
            final ArrayList<String> listTag = new ArrayList<>();
            for (int i = 0; i < note.getTag().size(); i++) {
                final TextView textViewTag = new TextView(context);
                textViewTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                service.getSpecify("tag", note.getTag().get(i).get_id(), new Service.getSpecifyCallBack() {
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
                        if(tag!=null){
                            textViewTag.setText(tag.getName() + ", ");
                        }else{
                            // do something with non exist tagg
                        }
                    }
                });
                textViewTag.setTextSize(14);
                linearLayout.addView(textViewTag);
            }
        }else{
            //do somthing
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (cs != null) {
            cs = new CustomFilter();
        }

        return cs;
    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<Note> filters = new ArrayList<>();
                for (int i = 0; i < arrayNote.size(); i++) {
                    if (arrayNote.get(i).getName().toUpperCase().contains(constraint)) {
                        Note note = new Note(arrayNote.get(i).getName());
                        filters.add(note);
                    }
                }

                results.count = filters.size();
                results.values = filters;
            } else {
                results.count = arrayNote.size();
                results.values = arrayNote;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayNote = (ArrayList<Note>) results.values;
            notifyDataSetChanged();
            notifyDataSetInvalidated();
        }
    }
    private class ViewHolder {
        TextView textView;
        TextView textViewTag;
        ImageView imageViewStar;
    }
}
