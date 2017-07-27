package com.example.ofek.todolist;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

import MyClasses.Task;
import worker8.com.github.radiogroupplus.RadioGroupPlus;

public class TasksList extends AppCompatActivity {
    RadioGroupPlus radioGroup;
    RadioButton radioLow,radioHigh,radioMid,radioNone;
    Button addBtn;
    TextClock timeView;
    EditText titleET,descET;
    ArrayList<Task> tasks=new ArrayList<>();
    ListView taskListV;
    ArrayAdapter adapter;
    ArrayList<String> tasksNames=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);
        setViews();
        setListeners();
        setTaskListV();
    }

    public void setTaskListV() {
        adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,tasksNames);
        taskListV.setAdapter(adapter);
        taskListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = findTaskByName(((TextView) view).getText().toString());
                AlertDialog taskDialog = getDialog(task);
                taskDialog.show();
            }
        });
    }
    private AlertDialog getDialog(final Task task){
        AlertDialog dialog=new AlertDialog.Builder(TasksList.this).create();
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Delete Task", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tasks.remove(task);
                tasksNames.remove(task.getTitle());
                adapter.notifyDataSetChanged();
            }
        });
        dialog.setTitle(task.getTitle());
        dialog.setMessage("Time: "+task.getTime()+"\n"+"Description:"+"\n"+task.getDesc()+"\n"+"Priority: "+task.getPriorityAsString());

        return dialog;
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
                Task task=new Task(titleET.getText().toString(),timeView.getText().toString(),getPriority(),descET.getText().toString());
                tasks.add(task);
                tasksNames.add(task.getTitle());
                adapter.notifyDataSetChanged();
                TextView textView= (TextView) taskListV.getAdapter().getView(tasksNames.size()-1,null,taskListV);
                textView.setBackgroundColor(Color.parseColor(getPriorityColorID(task.getPriority())));
                Toast.makeText(TasksList.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getPriority() {
        int id=radioGroup.getCheckedRadioButtonId();
        RadioButton temp= (RadioButton) findViewById(id);
        switch (temp.getText().toString()){
            case "Low":return 1;
            case "Mid":return 2;
            case "High":return 3;
            default:
                return 0;
        }
    }
    private String getPriorityColorID(int num){
        switch (num){
            case 1:return (""+android.R.color.holo_green_light);
            case 2:return (""+android.R.color.holo_orange_light);
            case 3:return (""+android.R.color.holo_red_light);
            default:return (""+R.color.gray);
        }
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
    private Task findTaskByName(String title){
        for (Task task:tasks){
            if (title.equals(task.getTitle()))
                return task;
        }
        return null;
    }


}
