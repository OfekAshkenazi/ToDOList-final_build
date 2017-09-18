//package dialogs;
//
//import android.app.TimePickerDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.support.v7.app.AlertDialog;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextClock;
//import android.widget.TimePicker;
//
//import com.example.ofek.todolist.R;
//import com.example.ofek.todolist.TasksList;
//
//import MyClasses.Task;
//
///**
// * Created by Ofek on 18-Aug-17.
// */
//
//public class MyDialogHelper {
//    private Context context;
//    EditText titleET;
//    EditText descET;
//    TextClock time;
//    Spinner prioritySpinner;
//
//    public MyDialogHelper(Context context) {
//        this.context = context;
//    }
//
//    public AlertDialog getEditDialog(final Task task){
//        AlertDialog dialog= new AlertDialog.Builder(context).create();
//        LayoutInflater inflater= LayoutInflater.from(context);
//        View dialogViews=inflater.inflate(R.layout.my_dialog_layout,null);
//        titleET=dialogViews.findViewById(R.id.titleETDialog);
//        titleET.setText(task.getTitle());
//        descET=dialogViews.findViewById(R.id.descETDialog);
//        descET.setText(task.getDesc());
//        time=dialogViews.findViewById(R.id.TextClockDialog);
//        time.setText(task.getTime());
//        time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getTimePickerDialog(time).show();
//
//            }
//        });
//        prioritySpinner=dialogViews.findViewById(R.id.prioritySpinnerDialog);
//        setSpinner();
//        prioritySpinner.setSelection(task.getPriority());
//        dialog.setView(dialogViews);
//        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Save", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                task.setTitle(titleET.getText().toString());
//                task.setDesc(descET.getText().toString());
//                task.setTime(time.getText().toString());
//                task.setPriority(prioritySpinner.getSelectedItemPosition());
//                TasksList.adapter.notifyDataSetChanged();
//            }
//        });
//        return dialog;
//    }
//    private void setSpinner(){
//        String[] priorityArr={"none","low","mid","high"};
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,priorityArr);
//        prioritySpinner.setAdapter(adapter);
//    }
//    public TimePickerDialog getTimePickerDialog(final TextClock timeView){
//        int hour=0,minutes=0;
//        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int Hour, int Minute) {
//                int hour,minutes;
//                hour=Hour;
//                minutes=Minute;
//                String sHour="";
//                String sMinute="";
//                if (hour<10)
//                    sHour+="0"+hour;
//                else
//                    sHour+=hour;
//                if (minutes<10)
//                    sMinute+="0"+minutes;
//                else
//                    sMinute+=minutes;
//                timeView.setText(sHour+":"+sMinute);
//            }
//        };
//        return new TimePickerDialog(context,timeSetListener,hour,minutes,false);
//    }
//
//}
