package MyAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.ofek.todolist.R;

import java.util.Collections;
import java.util.List;

import MyClasses.Task;

/**
 * Created by Ofek on 12-Sep-17.
 */

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.UpcomingVH>{
    List<Task> tasks= Collections.emptyList();
    Context context;
    int layoutId;



    public UpcomingAdapter(Context context, int layoutId, List<Task> tasks) {
        this.tasks = tasks;
        this.context = context;
        this.layoutId = layoutId;
    }

    @Override
    public UpcomingVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.upcoming_list_layout,null);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,parent.getHeight()));
        return new UpcomingVH(view);

    }

    @Override
    public void onBindViewHolder(UpcomingVH holder, int position) {
        Task task=tasks.get(position);
        holder.title.setText(task.getTitle());
        holder.time.setText(task.getTime());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private AlertDialog getDialog(final Task task){
        AlertDialog dialog=new AlertDialog.Builder(context).create();
        dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Delete Task", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tasks.remove(task);
                UpcomingAdapter.super.notifyDataSetChanged();
            }
        });
        dialog.setTitle(task.getTitle());
        dialog.setMessage("Time: "+task.getTime()+"\n"+"Description:"+"\n"+task.getDesc()+"\n"+"Priority: "+task.getPriorityAsString());

        return dialog;
    }

    class UpcomingVH extends RecyclerView.ViewHolder{
        TextClock time;
        TextView title;
        LinearLayout layout;
        public UpcomingVH(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.titleTV_adapter);
            layout=itemView.findViewById(R.id.layout_adapter);
            time=itemView.findViewById(R.id.timeTV_adapter);

        }
    }
}
