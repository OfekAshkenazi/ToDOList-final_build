package com.example.ofek.todolist;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextClock;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

import MyAdapters.MyAdapter;
import MyClasses.Task;
import worker8.com.github.radiogroupplus.RadioGroupPlus;

public class TasksList extends AppCompatActivity {
    public static RadioGroupPlus radioGroup;
    public static RadioButton radioLow,radioHigh,radioMid,radioNone;
    public static Button addBtn;
    public static TextClock timeView;
    public static EditText titleET,descET;
    ArrayList<Task> tasks=new ArrayList<>();
    public static ListView taskListV;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);
        setViews();
        setListeners();
        setTaskListV();
    }

    public void setTaskListV() {
        adapter= new MyAdapter(this, tasks);
        taskListV.setAdapter(adapter);
    }

    public static int getPriorityRadioId(Task task) {
        switch (task.getPriority()){
            case 1:return (R.id.radioLow);
            case 2:return (R.id.radioMedium);
            case 3:return (R.id.radioHigh);
            default:return (R.id.radioNoPriority);
        }
    }



    private void setViews(){
        radioGroup=(RadioGroupPlus) findViewById(R.id.radioG);
        radioHigh= (RadioButton) findViewById(R.id.radioHigh);
        radioMid= (RadioButton) findViewById(R.id.radioMedium);
        radioLow= (RadioButton) findViewById(R.id.radioLow);
        radioNone= (RadioButton) findViewById(R.id.radioNoPriority);
        addBtn= (Button) findViewById(R.id.AddBtn);
        timeView= (TextClock) findViewById(R.id.TextClock);
        titleET= (EditText) findViewById(R.id.TitleET);
        descET= (EditText) findViewById(R.id.descEt);
        taskListV= (ListView) findViewById(R.id.taskList);
    }

    int hour,minutes;
    private void setListeners(){
        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(TasksList.this,timeSetListener,hour,minutes,false);
                timePickerDialog.show();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleET.getText().equals("")||radioGroup.getCheckedRadioButtonId()==-1){
                    Toast.makeText(TasksList.this, "please make sure you entered the title and picked priority", Toast.LENGTH_SHORT).show();
                    return;
                }
                int id=radioGroup.getCheckedRadioButtonId();
                RadioButton temp= (RadioButton) findViewById(id);
                String priority=temp.getText().toString().toLowerCase();
                Task task=new Task(titleET.getText().toString(),timeView.getText().toString(),getPriority(priority),descET.getText().toString());
                tasks.add(task);
                adapter.notifyDataSetChanged();
                descET.setText(null);
                titleET.setText(null);
                radioGroup.clearCheck();
                timeView.setText(null);
            }
        });
    }

    private int getPriority(String priority) {
        if (priority.equals("low"))
            return 1;
        if (priority.equals("mid"))
            return 2;
        if (priority.equals("high"))
            return 3;
        return 0;
    }


    private TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int Hour, int Minute) {
            hour=Hour;
            minutes=Minute;
            String sHour="";
            String sMinute="";
            if (hour<10)
                sHour+="0"+hour;
            else
                sHour+=hour;
            if (minutes<10)
                sMinute+="0"+minutes;
            else
                sMinute+=minutes;
            timeView.setText(sHour+":"+sMinute);
        }
    };
    private AlertDialog getDialog(final Task task){
        AlertDialog dialog=new AlertDialog.Builder(TasksList.this).create();
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Delete Task", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tasks.remove(task);
                adapter.notifyDataSetChanged();
            }
        });
        dialog.setTitle(task.getTitle());
        dialog.setMessage("Time: "+task.getTime()+"\n"+"Description:"+"\n"+task.getDesc()+"\n"+"Priority: "+task.getPriorityAsString());

        return dialog;
    }


}
