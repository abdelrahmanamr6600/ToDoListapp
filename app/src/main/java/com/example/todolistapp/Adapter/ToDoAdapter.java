package com.example.todolistapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.AddNewTask;
import com.example.todolistapp.MainActivity;
import com.example.todolistapp.Model.ToDoModel;
import com.example.todolistapp.R;
import com.example.todolistapp.Utils.DataBaseHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    private List<ToDoModel> task_List;
    private MainActivity activity;
    private DataBaseHelper db;

    public ToDoAdapter(DataBaseHelper myDB,MainActivity activity)
    {
        this.activity = activity;
        this.db = myDB;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_layout,null,false);
       MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ToDoModel item = task_List.get(position);
        holder.mcheck.setText(item.getTask());
        holder.mcheck.setChecked(toboolean(item.getStatus()));
        holder.mcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (isChecked){
                     db.update_Status(item.getId(),1);
                 }else {
                     db.update_Status(item.getId(),0);
                 }
            }
        });

    }
    public  boolean toboolean ( int num ) { return num !=0; }
    public Context getcontext()
    {
        return activity;
    }

    public void setTasks(List<ToDoModel> task_List)
    {
        this.task_List = task_List;
        notifyDataSetChanged();
    }
     public void deleteTask(int position)
     {
         ToDoModel item = task_List.get(position);
         db.deleteTask(item.getId());
         task_List.remove(position);
         notifyItemRemoved(position);
     }
     public  void editTask(int position)
     {
         ToDoModel item = task_List.get(position);
         Bundle bundle = new Bundle();
         bundle.putInt("id",item.getId());
         bundle.putString("task",item.getTask());

         AddNewTask task = new AddNewTask();
         task.setArguments(bundle);
         task.show(activity.getSupportFragmentManager(),task.getTag());

     }
    @Override
    public int getItemCount() {
        return task_List.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
         CheckBox mcheck ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mcheck = itemView.findViewById(R.id.chekbox);
        }
    }
}
