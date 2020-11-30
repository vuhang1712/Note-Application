package com.teamfighttatic.HUS_OOP_ToDoApplication.apiService;

import com.teamfighttatic.HUS_OOP_ToDoApplication.Filter;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Note;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tag;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tasks;
import com.teamfighttatic.HUS_OOP_ToDoApplication.TodoList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class Service {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://restful-api-1503.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    public interface getFilterCallBack {
        void filterCallBack(List<Filter> listFilter);
    }
    public void getListFilter(String id, final getFilterCallBack getFilterCallBack){
        Call<List<Filter>> call = jsonPlaceHolderApi.getListFilter(id);
        call.enqueue(new Callback<List<Filter>>() {
            @Override
            public void onResponse(Call<List<Filter>> call, Response<List<Filter>> response) {
                if(response.isSuccessful()){
                    List<Filter> listFilter = response.body();
                        getFilterCallBack.filterCallBack(listFilter);
                }
            }

            @Override
            public void onFailure(Call<List<Filter>> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

    public void delete(String id, String ibase){
        Call<Ibase> call = jsonPlaceHolderApi.delete(id, ibase);
        call.enqueue(new Callback<Ibase>() {
            @Override
            public void onResponse(Call<Ibase> call, Response<Ibase> response) {
                if(response.isSuccessful()){
                    System.out.println("ok delete ibase" + response.body());
                }else{}
            }
            @Override
            public void onFailure(Call<Ibase> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

    public void update(String id, Ibase ibaseExtends){
        if(ibaseExtends instanceof Tag){
            Call<Tag> call = jsonPlaceHolderApi.updateTag(id, (Tag) ibaseExtends);
            call.enqueue(new Callback<Tag>() {
                @Override
                public void onResponse(Call<Tag> call, Response<Tag> response) {
                    if(response.isSuccessful()){
                        System.out.println("ok update" + response.body());
                    }else{
                        System.out.println("k dc dau  ");
                    }
                }

                @Override
                public void onFailure(Call<Tag> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                }
            });
        }else if(ibaseExtends instanceof Tasks){
            Call<Tasks> call = jsonPlaceHolderApi.updateTask(id, (Tasks) ibaseExtends);
            call.enqueue(new Callback<Tasks>() {
                @Override
                public void onResponse(Call<Tasks> call, Response<Tasks> response) {
                    if(response.isSuccessful()){
                        System.out.println("ok update" + response.body());
                    }else{
                        System.out.println("k  dc dau");
                    }
                }

                @Override
                public void onFailure(Call<Tasks> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                }
            });
        }else if(ibaseExtends instanceof Note){
            Call<Note> call = jsonPlaceHolderApi.updateNote(id, (Note) ibaseExtends);
            call.enqueue(new Callback<Note>() {
                @Override
                public void onResponse(Call<Note> call, Response<Note> response) {
                    if(response.isSuccessful()){
                        System.out.println("ok update" + response.body());
                    }else{
                        System.out.println("k dc dau");
                    }
                }

                @Override
                public void onFailure(Call<Note> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                }
            });
        }else if(ibaseExtends instanceof TodoList){
            Call<TodoList> call = jsonPlaceHolderApi.updateTodoList(id, (TodoList) ibaseExtends);
            call.enqueue(new Callback<TodoList>() {
                @Override
                public void onResponse(Call<TodoList> call, Response<TodoList> response) {
                    if(response.isSuccessful()){
                        System.out.println("ok update" + response.body());
                    }else{
                        System.out.println("k dc dau");
                    }
                }

                @Override
                public void onFailure(Call<TodoList> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                }
            });
        }
    }

    public void create(Ibase ibaseExtends){
        if(ibaseExtends instanceof Tasks){
            Call<Tasks> call = jsonPlaceHolderApi.createTask((Tasks) ibaseExtends);
            call.enqueue(new Callback<Tasks>() {
                @Override
                public void onResponse(Call<Tasks> call, Response<Tasks> response) {
                    if(response.isSuccessful()){
                        System.out.println("ok create" + response.body());
                    }else{}
                }

                @Override
                public void onFailure(Call<Tasks> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                }
            });
        }else if(ibaseExtends instanceof Tag){
            Call<Tag> call = jsonPlaceHolderApi.createTag((Tag) ibaseExtends);
            call.enqueue(new Callback<Tag>() {
                @Override
                public void onResponse(Call<Tag> call, Response<Tag> response) {
                    if(response.isSuccessful()){
                        System.out.println("ok create" + response.body());
                    }else{}
                }

                @Override
                public void onFailure(Call<Tag> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                }
            });
        }else if(ibaseExtends instanceof Note){
            Call<Note> call = jsonPlaceHolderApi.createNote((Note) ibaseExtends);
            call.enqueue(new Callback<Note>() {
                @Override
                public void onResponse(Call<Note> call, Response<Note> response) {
                    if(response.isSuccessful()){
                        System.out.println("ok create" + response.body());
                    }else{}
                }

                @Override
                public void onFailure(Call<Note> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                }
            });
        }else if(ibaseExtends instanceof TodoList){
            Call<TodoList> call = jsonPlaceHolderApi.createTodoList((TodoList) ibaseExtends);
            call.enqueue(new Callback<TodoList>() {
                @Override
                public void onResponse(Call<TodoList> call, Response<TodoList> response) {
                    if(response.isSuccessful()){
                        System.out.println("ok create" + response.body());
                    }else{}
                }
                @Override
                public void onFailure(Call<TodoList> call, Throwable t) {
                    System.out.println(t.getLocalizedMessage());
                }
            });
        }
    }

    public interface getCallBack {
        void getAllTasks(List<Tasks> tasks);
        void getAllNote(List<Note> note);
        void getAllTodoList(List<TodoList> todoLists);
        void getAllTag(List<Tag> tags);
    }
    public void getAll(String ibase, final getCallBack getCallBack){
        if(ibase == "tasks"){
            Call<List<Tasks>> call = jsonPlaceHolderApi.getAll();
            call.enqueue(new Callback<List<Tasks>>() {
                @Override
                public void onResponse(Call<List<Tasks>> call, Response<List<Tasks>> response) {
                    if(response.isSuccessful()){
                        List<Tasks> allTask = response.body();
                        getCallBack.getAllTasks(allTask);
                    }else{
                        return;
                    }
                }

                @Override
                public void onFailure(Call<List<Tasks>> call, Throwable t) {
                    System.out.println("Tasks" + t.getLocalizedMessage());
                }
            });
        }else if(ibase == "note"){
           Call<List<Note>> call = jsonPlaceHolderApi.getAllNote();
           call.enqueue(new Callback<List<Note>>() {
               @Override
               public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                   if(response.isSuccessful()){
                       List<Note> allNote = response.body();
                       getCallBack.getAllNote(allNote);
                   }else{
                       return;
                   }
               }

               @Override
               public void onFailure(Call<List<Note>> call, Throwable t) {
                   System.out.println("Note" + t.getLocalizedMessage());
               }
           });
        }else if(ibase == "tag"){
            Call<List<Tag>> call = jsonPlaceHolderApi.getAllTag();
            call.enqueue(new Callback<List<Tag>>() {
                @Override
                public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                    if(response.isSuccessful()){
                        List<Tag> allTag = response.body();
                        getCallBack.getAllTag(allTag);
                    }else{
                        return;
                    }
                }

                @Override
                public void onFailure(Call<List<Tag>> call, Throwable t) {
                    System.out.println("Note" + t.getLocalizedMessage());
                }
            });
        }else if(ibase == "todoList"){
            Call<List<TodoList>> call = jsonPlaceHolderApi.getAllTodoList();
            call.enqueue(new Callback<List<TodoList>>() {
                @Override
                public void onResponse(Call<List<TodoList>> call, Response<List<TodoList>> response) {
                    if(response.isSuccessful()){
                        List<TodoList> allTodoList = response.body();
                        getCallBack.getAllTodoList(allTodoList);
                    }else {
                        return;
                    }
                }

                @Override
                public void onFailure(Call<List<TodoList>> call, Throwable t) {
                    System.out.println("Note" + t.getLocalizedMessage());
                }
            });
        }
    }

    public interface getSpecifyCallBack{
        void getTasks(Tasks tasks);
        void getNote(Note note);
        void getTodoList(TodoList todoList);
        void getTag(Tag tag);
    }
    public void getSpecify(String ibase, String id, final getSpecifyCallBack getSpecifyCallBack){
        if(ibase == "tasks"){
            Call<Tasks> call = jsonPlaceHolderApi.getSpecifyTask(id);
            call.enqueue(new Callback<Tasks>() {
                @Override
                public void onResponse(Call<Tasks> call, Response<Tasks> response) {
                    if(response.isSuccessful()){
                      Tasks tasks = response.body();
                      getSpecifyCallBack.getTasks(tasks);
                    }else{
                        return;
                    }
                }

                @Override
                public void onFailure(Call<Tasks> call, Throwable t) {
                    System.out.println("Note" + t.getLocalizedMessage());
                }
            });
        }else if(ibase == "note"){
            Call<Note> call = jsonPlaceHolderApi.getSpecifyNote(id);
            call.enqueue(new Callback<Note>() {
                @Override
                public void onResponse(Call<Note> call, Response<Note> response) {
                    if(response.isSuccessful()){
                        Note note = response.body();
                        getSpecifyCallBack.getNote(note);
                    }else{
                        return;
                    }
                }

                @Override
                public void onFailure(Call<Note> call, Throwable t) {
                    System.out.println("Note" + t.getLocalizedMessage());
                }
            });
        }else if(ibase == "todoList"){
            Call<TodoList> call = jsonPlaceHolderApi.getSpecifyTodoList(id);
            call.enqueue(new Callback<TodoList>() {
                @Override
                public void onResponse(Call<TodoList> call, Response<TodoList> response) {
                    if(response.isSuccessful()){
                        TodoList todoList = response.body();
                        getSpecifyCallBack.getTodoList(todoList);
                    }else{
                        return;
                    }
                }

                @Override
                public void onFailure(Call<TodoList> call, Throwable t) {
                    System.out.println("Note" + t.getLocalizedMessage());
                }
            });
        }else if(ibase == "tag"){
            Call<Tag> call = jsonPlaceHolderApi.getSpecifyTag(id);
            call.enqueue(new Callback<Tag>() {
                @Override
                public void onResponse(Call<Tag> call, Response<Tag> response) {
                    if(response.isSuccessful()){
                        Tag tag = response.body();
                        getSpecifyCallBack.getTag(tag);
                    }else{
                        return;
                    }
                }

                @Override
                public void onFailure(Call<Tag> call, Throwable t) {
                    System.out.println("Note" + t.getLocalizedMessage());
                }
            });
        }
    }

}
