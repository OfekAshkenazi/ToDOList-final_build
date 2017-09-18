package com.example.ofek.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import MyAdapters.MyAdapter;
import MyClasses.Date;
import MyClasses.Task;
import SQliteDatabse.TodoListSQLiteDatabase;

public class TasksList extends AppCompatActivity {
//    public static RadioGroupPlus radioGroup;
//    public static RadioButton radioLow,radioHigh,radioMid,radioNone;
//    public static Button addBtn;
//    public static TextClock timeView;
//    public static EditText titleET,descET;
    Toolbar toolbar;
    FloatingActionButton addTaskBtn;
    ArrayList<Task> tasks=new ArrayList<>();
    public static ListView taskListV;
    public static MyAdapter adapter;
    private static TodoListSQLiteDatabase db;
    public static final int ADD_TASK_CALL=1111;
    private static int sortOrder=1;
    private static Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);
        date= (Date) getIntent().getExtras().get("DATE");
        try{
            initializeTasksArray();
        }
        catch (Exception e){
            Log.e("DATABASE LOADING ERROR ",e.getMessage());
        }
        setViews();
        setListeners();
        setTaskListV();
    }

    public void setTaskListV() {
        adapter= new MyAdapter(this, tasks,date);
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
//        radioGroup=(RadioGroupPlus) findViewById(R.id.radioG);
//        radioHigh= (RadioButton) findViewById(R.id.radioHigh);
//        radioMid= (RadioButton) findViewById(R.id.radioMedium);
//        radioLow= (RadioButton) findViewById(R.id.radioLow);
//        radioNone= (RadioButton) findViewById(R.id.radioNoPriority);
//        addBtn= (Button) findViewById(R.id.AddBtn);
//        timeView= (TextClock) findViewById(R.id.TextClock);
//        titleET= (EditText) findViewById(R.id.TitleET);
//        descET= (EditText) findViewById(R.id.descET);
        toolbar= (Toolbar) findViewById(R.id.taskListToolbar);
        taskListV= (ListView) findViewById(R.id.taskList);
        addTaskBtn= (FloatingActionButton) findViewById(R.id.addTaskBtn);
        toolbar.setTitle(toolbar.getTitle()+"-"+date.getAsString());
    }


    private void setListeners(){
//        timeView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (titleET.getText().equals("")||radioGroup.getCheckedRadioButtonId()==-1){
//                    Toast.makeText(TasksList.this, "please make sure you entered the title and picked priority", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                int id=radioGroup.getCheckedRadioButtonId();
//                RadioButton temp= (RadioButton) findViewById(id);
//                String priority=temp.getText().toString().toLowerCase();
//                Task task=new Task(titleET.getText().toString(),timeView.getText().toString(),getPriority(priority),descET.getText().toString());
//                tasks.add(task);
//                adapter.notifyDataSetChanged();
//                descET.setText(null);
//                titleET.setText(null);
//                radioGroup.clearCheck();
//                timeView.setText(null);
//            }
//        });
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(view.getContext(),AddTaskActivity.class);
                startActivityForResult(intent,ADD_TASK_CALL);
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

    @Override
    protected void onStop() {
        super.onStop();
        if (tasks.size()==db.getAllData().getCount()) return;
        initializeTasksArray();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==ADD_TASK_CALL){
            if (resultCode==AddTaskActivity.INSERTED_RESULT){
                Task task=(Task) data.getExtras().get("NEW_TASK");
                tasks.add(task);
                adapter.notifyDataSetChanged();
                db.insert(task);
                db.closeDB();
            }
        }
    }

    public void initializeTasksArray() {
        db=TodoListSQLiteDatabase.getDataBase(date);
        Cursor cursor=db.getAllData();
        if (db==null) return;
        if (!tasks.isEmpty()) tasks.clear();
        if (cursor.getCount()<=0)
            return;
        for(int i=0;cursor.moveToNext();i++){
            Task task=new Task(cursor.getString(1),cursor.getString(3),cursor.getInt(4),cursor.getString(2));
            tasks.add(i,task);
        }
        Task.sortTasksArray(tasks,sortOrder);
    }

    public static TodoListSQLiteDatabase getDb() {
        return db;
    }




}
