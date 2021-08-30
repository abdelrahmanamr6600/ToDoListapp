package com.example.todolistapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.todolistapp.Model.ToDoModel;
import com.example.todolistapp.R;
import com.example.todolistapp.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
     static final String Tag = "AddNewTask";
    private Button save_btn;
    private EditText task_edit_text;
    private DataBaseHelper db;


    public static AddNewTask newInstance()
    {
        return new AddNewTask();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_new_task,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        task_edit_text =  view.findViewById(R.id.task_text);
        save_btn = view.findViewById(R.id.save_btn);

        db = new DataBaseHelper(getActivity());

        boolean isUpdate = false;

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            isUpdate = true;
            String task =  bundle.getString("task");
            task_edit_text.setText(task);

            if (task.length() > 0 )
            {
                save_btn.setEnabled(false);
            }
        }
        task_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(""))
                {
                    save_btn.setEnabled(false);
                    save_btn.setBackgroundColor(Color.GRAY);
                }
                else{
                    save_btn.setEnabled(true);
                    save_btn.setBackgroundColor(getResources().getColor(R.color.ColorPrimary));
                    save_btn.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        boolean finalIsUpdate = isUpdate;

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = task_edit_text.getText().toString();
                ToDoModel item = new ToDoModel();
                if (finalIsUpdate){
                    db.updateTask(bundle.getInt("id"),text);
                }
                else
                {

                    item.setTask(text);
                    item.setStatus(0);
                    db.insertTask(item);

                }
                dismiss();
            }
        });


    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListner){
            ((OnDialogCloseListner)activity).onDialogClose(dialog);

        }
    }


}

