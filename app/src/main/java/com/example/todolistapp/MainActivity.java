package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todolistapp.Adapter.ToDoAdapter;
import com.example.todolistapp.Model.ToDoModel;
import com.example.todolistapp.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListner {
   private RecyclerView mRecycler;
   private FloatingActionButton fab ;
   private DataBaseHelper db;
   private List<ToDoModel> tasks_List;
   private ToDoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecycler = findViewById(R.id.tasksview);
        fab = findViewById(R.id.fba);
        db = new DataBaseHelper(MainActivity.this);
        tasks_List = new ArrayList<>();
        adapter = new ToDoAdapter(db,MainActivity.this);

        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(adapter);
        tasks_List = db.getallTasks();
        Collections.reverse(tasks_List);
        adapter.setTasks(tasks_List);



fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.Tag);
    }
});
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecycler);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        tasks_List = db.getallTasks();
        Collections.reverse(tasks_List);
        adapter.setTasks(tasks_List);
        adapter.notifyDataSetChanged();
    }
}