package com.teamfighttatic.HUS_OOP_ToDoApplication.apiService;

import com.teamfighttatic.HUS_OOP_ToDoApplication.Note;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tasks;
import com.teamfighttatic.HUS_OOP_ToDoApplication.TodoList;

import java.util.ArrayList;

public class Ibase {

    public static ArrayList<Ibase> listIbase = new ArrayList<>();

    public static void findIbase(int id, Ibase ibase){
        if (ibase instanceof Tasks){
            System.out.println("datttt");
        }else if(ibase instanceof Note){

        }else{

        }
    }

    public static void findIbaseStar(){

    }
}
