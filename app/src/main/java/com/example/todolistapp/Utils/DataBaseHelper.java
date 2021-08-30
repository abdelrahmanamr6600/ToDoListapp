package com.example.todolistapp.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todolistapp.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
 public static final String DB_NAME = "TASKDB";
    public static final String task_table = "tasks";
    public static final String col1_tasks= "ID";
    public static final String col2_tasks= "TASK";
    public static final String col3_tasks= "STATUS";
    public static final int db_VERSION = 4;
    SQLiteDatabase db ;


    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME,null, db_VERSION);
    }


 @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if  not exists "+task_table+" ( ID INTEGER PRIMARY KEY   AUTOINCREMENT,  TASK text , STATUS INTEGER) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + task_table);
        onCreate(db);

    }

public void insertTask(ToDoModel model)
{
    db = this.getWritableDatabase();
    ContentValues valuse = new ContentValues();
    valuse.put(col2_tasks, model.getTask());
    valuse.put(col3_tasks, 0);
    db.insert(task_table,null,valuse);
}
public void updateTask( int id , String task)
{
    db = this.getWritableDatabase();
    ContentValues valuse = new ContentValues();
    valuse.put(col2_tasks,task);
    db.update(task_table,valuse, "ID=?", new String[]{String.valueOf(id)});

}
 public void update_Status( int id , int status){
     db = this.getWritableDatabase();
     ContentValues valuse = new ContentValues();
     valuse.put(col3_tasks,status);
     db.update(task_table,valuse, "ID=?", new String[]{String.valueOf(id)});

 }
 public void deleteTask(int id)
 {
     db = this.getWritableDatabase();
     ContentValues valuse = new ContentValues();
     db.delete(task_table,"ID=?",new String[]{String.valueOf(id)});
 }
 public List<ToDoModel> getallTasks(){
        db= this.getWritableDatabase();
     Cursor cursor = null;
     List<ToDoModel> tasks_List = new ArrayList<>();
     db.beginTransaction();
     try {
         cursor = db.query(task_table,null,null,null,null
         ,null,null);
         if (cursor !=null){
             if (cursor.moveToFirst()){
                 do {
                     ToDoModel task = new ToDoModel();
                     task.setId(cursor.getInt(cursor.getColumnIndex(col1_tasks)));
                     task.setTask(cursor.getString(cursor.getColumnIndex(col2_tasks)));
                     task.setStatus(cursor.getInt(cursor.getColumnIndex(col3_tasks)));
                     tasks_List.add(task);
                 }while (cursor.moveToNext());
             }

         }
     } finally {
         db.endTransaction();
         cursor.close();
     }
     return  tasks_List;
 }
}








