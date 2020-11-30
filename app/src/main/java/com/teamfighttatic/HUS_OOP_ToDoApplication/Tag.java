package com.teamfighttatic.HUS_OOP_ToDoApplication;

import com.teamfighttatic.HUS_OOP_ToDoApplication.apiService.Ibase;

public class Tag extends Ibase {
    private String name;
    private String _id;

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String get_id() {
        return _id;
    }

    public Tag(String name) {
        this.name = name;
    }
    public Tag() {
    }

//    @Override
//    public int hashCode() {
//        return get_id().hashCode();
//    }
//
//    @Override
//    public boolean equals(@Nullable Object obj) {
//        if(obj.getClass() != this.getClass()) return false;
//        Tag tag = (Tag) obj;
//        return this._id == tag._id;
//    }
}
