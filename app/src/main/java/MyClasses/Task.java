package MyClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

/**
 * Created by Ofek on 25-Jul-17.
 */


public class Task implements Serializable {
    String Title;
    CharSequence Time;
    String description;
    int priority;
    int id;

    public Task(String title, CharSequence time, int priority,String desc) {
        Title = title;
        Time = time;
        this.description = desc;
        this.priority = priority;
        id= UUID.randomUUID().hashCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public CharSequence getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String content) {
        description = content;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    public String getPriorityAsString(){
        switch (priority){
            case 1:return "Low";
            case 2:return "Medium";
            case 3:return "High";
            default:
                return "none";
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (priority != task.priority) return false;
        if (!Title.equals(task.Title)) return false;
        if (Time != null ? !Time.equals(task.Time) : task.Time != null) return false;
        return description != null ? description.equals(task.description) : task.description == null;

    }
    public static void sortTasksArray(ArrayList<Task> tasks,int field) {
        if (field==1)
            Collections.sort(tasks,new TaskPriorityComparator());
        if (field==2)
            Collections.sort(tasks,new TaskTitleComparator());

    }

}

    class TaskPriorityComparator implements Comparator<Task>{

        @Override
        public int compare(Task task, Task t1) {
            if (task.getPriority()>t1.getPriority()) return 0;
            return 1;
        }
    }
    class TaskTitleComparator implements Comparator<Task>{

        @Override
        public int compare(Task task, Task t1) {
            return task.getTitle().compareTo(t1.getTitle());
        }
    }

