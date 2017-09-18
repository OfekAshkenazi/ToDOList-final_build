package com.example.ofek.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import MyAdapters.UpcomingAdapter;
import MyClasses.Date;
import MyClasses.Task;
import SQliteDatabse.TodoListSQLiteDatabase;

public class MainActivity extends AppCompatActivity {
    RecyclerView list;
    CalendarView calendarView;
    UpcomingAdapter adapter;
    ArrayList<Task> upcomingTasks;
    TodoListSQLiteDatabase db;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView= (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Date date=new Date(i,i1,i2);
                Intent intent=new Intent(MainActivity.this,TasksList.class);
                intent.putExtra("DATE",date);
                startActivity(intent);
            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String selectedDate = sdf.format(new java.util.Date(calendarView.getDate()));
        date=new Date(Integer.parseInt(selectedDate.substring(6)),Integer.parseInt(selectedDate.substring(3,5)),Integer.parseInt(selectedDate.substring(0,2)));
        db=TodoListSQLiteDatabase.TodoListSQLiteDatabaseBuilder(this,date);
        initializeUpcomingTasks();
        list= (RecyclerView) findViewById(R.id.upcoming_list);
        adapter=new UpcomingAdapter(this,R.layout.upcoming_list_layout,upcomingTasks);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeUpcomingTasks() {
        upcomingTasks=new ArrayList<>();
        Cursor cursor=db.getAllData();
        if (db==null) return;
        if (cursor.getCount()<=0)
            return;
        for(int i=0;cursor.moveToNext();i++){
            Task task=new Task(cursor.getString(1),cursor.getString(3),cursor.getInt(4),cursor.getString(2));
            upcomingTasks.add(i,task);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor=db.getAllData();
        if (upcomingTasks.size()==cursor.getCount())return;
        if (upcomingTasks.size()>cursor.getCount()){
            for (int i=cursor.getCount();i<upcomingTasks.size();i++){
                upcomingTasks.remove(i);
            }
        }
        else {
            cursor.move(upcomingTasks.size());
            for(;cursor.moveToNext();) {
                Task task = new Task(cursor.getString(1), cursor.getString(3), cursor.getInt(4), cursor.getString(2));
                upcomingTasks.add(task);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
