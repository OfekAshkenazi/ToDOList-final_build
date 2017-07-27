package com.example.ofek.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextClock;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioLow,radioHigh,radioMid,radioNone;
    Button addBtn;
    TextClock timeView;
    EditText titleET,descET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
