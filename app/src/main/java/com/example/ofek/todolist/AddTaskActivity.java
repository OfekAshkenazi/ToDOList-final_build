package com.example.ofek.todolist;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextClock;
import android.widget.TimePicker;
import android.widget.Toast;

import MyClasses.Task;
import worker8.com.github.radiogroupplus.RadioGroupPlus;

public class AddTaskActivity extends Activity {
    public static RadioGroupPlus radioGroup;
    public static RadioButton radioLow,radioHigh,radioMid,radioNone;
    public static Button addBtn;
    public static TextClock timeView;
    public static EditText titleET,descET;
    public static final int INSERTED_RESULT=2222;
    public static final int NOT_INSERTED_RESULT=3333;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        setViews();
        setListeners();
    }

    private void setViews() {
        radioGroup = (RadioGroupPlus) findViewById(R.id.radioG);
        radioHigh = (RadioButton) findViewById(R.id.radioHigh);
        radioMid = (RadioButton) findViewById(R.id.radioMedium);
        radioLow = (RadioButton) findViewById(R.id.radioLow);
        radioNone = (RadioButton) findViewById(R.id.radioNoPriority);
        addBtn = (Button) findViewById(R.id.AddBtn);
        timeView = (TextClock) findViewById(R.id.TextClock);
        titleET = (EditText) findViewById(R.id.TitleET);
        descET = (EditText) findViewById(R.id.descET);
    }



    private void setListeners(){
        final Intent intent=getIntent();
        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTimePickerDialog(timeView).show();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleET.getText().equals("")||radioGroup.getCheckedRadioButtonId()==-1){
                    Toast.makeText(AddTaskActivity.this, "please make sure you entered the title and picked priority", Toast.LENGTH_SHORT).show();
                    return;
                }
                int id=radioGroup.getCheckedRadioButtonId();
                RadioButton temp= (RadioButton) findViewById(id);
                String priority=temp.getText().toString().toLowerCase();
                Task task=new Task(titleET.getText().toString(),timeView.getText(),getPriority(priority),descET.getText().toString());
                intent.putExtra("NEW_TASK",task);
                setResult(INSERTED_RESULT,intent);
                finish();
//
//                    Toast.makeText(AddTaskActivity.this, "There was an error, please try again", Toast.LENGTH_SHORT).show();
//                    setResult(NOT_INSERTED_RESULT,intent);
//                    finish();

            }
        });
    }
    public TimePickerDialog getTimePickerDialog(final TextClock timeView){
        int hour=0,minutes=0;
        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int Hour, int Minute) {
                int hour,minutes;
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
        return new TimePickerDialog(AddTaskActivity.this,timeSetListener,hour,minutes,false);
    }

    public static int getPriorityRadioId(Task task) {
        switch (task.getPriority()){
            case 1:return (R.id.radioLow);
            case 2:return (R.id.radioMedium);
            case 3:return (R.id.radioHigh);
            default:return (R.id.radioNoPriority);
        }
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
}
