package MyAdapters;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.ofek.todolist.R;

import java.util.ArrayList;

import MyClasses.Date;
import MyClasses.Task;
import SQliteDatabse.TodoListSQLiteDatabase;

public class MyAdapter extends BaseAdapter {
    ArrayList<Task> tasks;
    Context context;
    Date currentDate;
    public MyAdapter(Context context, ArrayList<Task> tasks, Date date) {
        this.tasks = tasks;
        this.context = context;
        currentDate=date;
    }

    @Override
    public int getCount() {

        return tasks.size();
    }

    @Override
    public Object getItem(int p1) {

        return tasks.get(p1);
    }

    @Override
    public long getItemId(int i) {

        return (long) i;
    }


    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.my_listview_adapter, parent, false);
        TextView titleTV = (TextView) row.findViewById(R.id.my_listview_adapterTV);
        Button priorityBtn = row.findViewById(R.id.buttonPriorityBackground);
        Task task = tasks.get(i);
        titleTV.setText(task.getTitle());
        int color = getPriorityColorID(task.getPriority());
        ColorStateList colorStateList;
        priorityBtn.setBackgroundTintList(ColorStateList.valueOf(getPriorityColorID(task.getPriority())));
        setListeners(row, i);
        return row;
    }

    private void setListeners(View row, final int index) {
        TextView titleTV = (TextView) row.findViewById(R.id.my_listview_adapterTV);
        final ImageButton buttonEdit = (ImageButton) row.findViewById(R.id.my_listview_adapterImageEdit);
        final ImageButton buttonFinish = (ImageButton) row.findViewById(R.id.my_listview_adapterImageFinish);
        titleTV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (buttonFinish.getVisibility() == View.GONE) {
                    buttonEdit.setVisibility(View.GONE);
                    buttonFinish.setVisibility(View.VISIBLE);
                } else {
                    buttonFinish.setVisibility(View.GONE);
                    buttonEdit.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialogHelper helper = new MyDialogHelper(context,currentDate);
                AlertDialog dialog = helper.getEditDialog(tasks.get(index));
                dialog.show();
                dialog.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
//                Toast.makeText(context, "id:"+(parent.getId()), Toast.LENGTH_SHORT).show();
//                Task task = tasks.get(index);
//                TasksList.titleET.setText(task.getTitle());
//                TasksList.descET.setText(task.getDesc());
//                radioGroup.check(getPriorityRadioId(task));
//                timeView.setText(task.getTime());
            }
        });
        titleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = tasks.get(index);
                AlertDialog taskDialog = ShowTaskDialog(task);
                taskDialog.show();
            }
        });
    }

    private int getPriorityColorID(int num) {
        if (num == 1) {
            return Color.rgb(153, 204, 0);
        }
        if (num == 2) {
            return Color.rgb(255, 187, 51);
        }
        if (num == 3) {
            return Color.rgb(255, 68, 68);
        }
        if (num == 0) {
            return Color.rgb(211, 211, 211);
        }
        return -1;
    }




    public AlertDialog ShowTaskDialog(final Task task) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Delete Task", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tasks.remove(task);
                notifyDataSetChanged();
                TodoListSQLiteDatabase db=TodoListSQLiteDatabase.getDataBase(currentDate);
                db.deleteData(task);
            }
        });
        dialog.setTitle(task.getTitle());
        dialog.setMessage("Time: " + task.getTime() + "\n" + "Description:" + "\n" + task.getDesc() + "\n" + "Priority: " + task.getPriorityAsString());

        return dialog;
    }
}

class MyDialogHelper {
    private Context context;
    EditText titleET;
    EditText descET;
    TextClock time;
    Spinner prioritySpinner;
    Date currentDate;
    public MyDialogHelper(Context context,Date date) {
        this.context = context;
        currentDate=date;
    }

    public AlertDialog getEditDialog(final Task task) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogViews = inflater.inflate(R.layout.my_dialog_layout, null);
        titleET = dialogViews.findViewById(R.id.titleETDialog);
        titleET.setText(task.getTitle());
        descET = dialogViews.findViewById(R.id.descETDialog);
        descET.setText(task.getDesc());
        time = dialogViews.findViewById(R.id.TextClockDialog);
        time.setText(task.getTime());
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTimePickerDialog(time).show();

            }
        });
        prioritySpinner = dialogViews.findViewById(R.id.prioritySpinnerDialog);
        setSpinner();
        prioritySpinner.setSelection(task.getPriority());
        dialog.setView(dialogViews);
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Task task1=new Task(titleET.getText().toString(),time.getText().toString(),prioritySpinner.getSelectedItemPosition(),descET.getText().toString());
                TodoListSQLiteDatabase database=TodoListSQLiteDatabase.getDataBase(currentDate);
                database.deleteData(task);
                task1.setId(task.getId());
                database.insert(task1);
            }
        });
        return dialog;
    }


    public TimePickerDialog getTimePickerDialog(final TextView timeView) {
        int hour = 0, minutes = 0;
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int Hour, int Minute) {
                int hour, minutes;
                hour = Hour;
                minutes = Minute;
                String sHour = "";
                String sMinute = "";
                if (hour < 10)
                    sHour += "0" + hour;
                else
                    sHour += hour;
                if (minutes < 10)
                    sMinute += "0" + minutes;
                else
                    sMinute += minutes;
                timeView.setText(sHour + ":" + sMinute);
            }
        };
        return new TimePickerDialog(context, timeSetListener, hour, minutes, false);
    }




    private void setSpinner() {
        String[] priorityArr = {"none", "low", "mid", "high"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, priorityArr);
        prioritySpinner.setAdapter(adapter);
    }

}
