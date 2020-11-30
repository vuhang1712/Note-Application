package com.teamfighttatic.HUS_OOP_ToDoApplication.apiService;

import com.teamfighttatic.HUS_OOP_ToDoApplication.Filter;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Note;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tag;
import com.teamfighttatic.HUS_OOP_ToDoApplication.Tasks;
import com.teamfighttatic.HUS_OOP_ToDoApplication.TodoList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {
    //filter tag
    @POST("findTag/{id}")
    Call<List<Filter>> getListFilter(@Path("id") String id);

    // test API delete
    @DELETE("{ibase}/{id}")
    Call<Ibase> delete(@Path("id") String id, @Path("ibase") String ibase);
    @GET("{ibase}")
    Call<List<Ibase>> getAllApi(@Path("ibase") String ibase);

    //tasks handle API
    @GET("tasks/{id}")
    Call<Tasks> getSpecifyTask(@Path("id") String id);

    @GET("tasks")
    Call<List<Tasks>> getAll();

    @POST("tasks")
    Call<Tasks> createTask(@Body Tasks getAllTasks);

    @PATCH("tasks/{id}")
    Call<Tasks> updateTask(@Path("id") String id, @Body Tasks getAllTasks);

    @DELETE("tasks/{id}")
    Call<Tasks> deleteTASK(@Path("id") String id);

    // tag handle API
    @GET("tag")
    Call<List<Tag>> getAllTag();

    @GET("tag/{id}")
    Call<Tag> getSpecifyTag(@Path("id") String id);

    @POST("tag")
    Call<Tag> createTag(@Body Tag tag);

    @PATCH("tag/{id}")
    Call<Tag> updateTag(@Path("id") String id, @Body Tag tag);

    @DELETE("tag/{id}")
    Call<Tag> deleteTag(@Path("id") String id);
    // todoList handle API

    @GET("todoList")
    Call<List<TodoList>> getAllTodoList();

    @GET("todoList/{id}")
    Call<TodoList> getSpecifyTodoList(@Path("id") String id);

    @POST("todoList")
    Call<TodoList> createTodoList(@Body TodoList todoList);

    @PATCH("todoList/{id}")
    Call<TodoList> updateTodoList(@Path("id") String id, @Body TodoList todoList);

    @DELETE("todoList/{id}")
    Call<TodoList> deleteTodoList(@Path("id") String id);


    // note handle API
    @GET("note")
    Call<List<Note>> getAllNote();

    @GET("note/{id}")
    Call<Note> getSpecifyNote(@Path("id") String id);

    @POST("note")
    Call<Note> createNote(@Body Note note);

    @PATCH("note/{id}")
    Call<Note> updateNote(@Path("id") String id, @Body Note note);

    @DELETE("note/{id}")
    Call<Note> deleteNote(@Path("id") String id);
}
