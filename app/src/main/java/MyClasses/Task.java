package MyClasses;

/**
 * Created by Ofek on 25-Jul-17.
 */

public class Task {
    String Title;
    String Time;
    String description;
    int priority;

    public Task(String title, String time, int priority,String desc) {
        Title = title;
        Time = time;
        this.description = desc;
        this.priority = priority;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTime() {
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
}
