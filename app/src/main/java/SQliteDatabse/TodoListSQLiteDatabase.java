package SQliteDatabse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import MyClasses.Date;
import MyClasses.Task;

/**
 * Created by Ofek on 18-Aug-17.
 */

public class TodoListSQLiteDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME="TASKDATABASE";
    public static final String TABLE_NAME="TASKSTABLE";
    public static final String UID="ID";
    public static final String COL_1="TITLE";
    public static final String COL_2="DESC";
    public static final String COL_3="TIME";
    public static final String COL_4="PRIORITY";
    public static final int DATABASE_VERSION=2;
    private Date date;
    private static TodoListSQLiteDatabase instance=null;
    private static Context context=null;

    private TodoListSQLiteDatabase(Context context,Date date) {
        super(context, DB_NAME+"--"+date.getAsString(), null, DATABASE_VERSION);
        this.date=date;
    }

    public static TodoListSQLiteDatabase TodoListSQLiteDatabaseBuilder(Context context1, Date date){
        if (instance==null){
            context=context1;
            instance=new TodoListSQLiteDatabase(context,date);
        }
        return instance;
    }
    public static TodoListSQLiteDatabase getDataBase(Date date){
        instance=new TodoListSQLiteDatabase(context,date);
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY,TITLE TEXT,DESC TEXT,TIME TEXT,PRIORITY INTEGER )");
        }
        catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME);
        onCreate(db);
    }

    public long insert(Task task){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(UID,task.getId());
        values.put(COL_1,task.getTitle());
        values.put(COL_2,task.getDesc());
        values.put(COL_3,task.getTime().toString());
        values.put(COL_4,task.getPriority());
        return db.insert(TABLE_NAME,null,values);
    }

    public boolean updateData(Task task,Task task1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UID,task.getId());
        values.put(COL_1,task1.getTitle());
        values.put(COL_2,task1.getDesc());
        values.put(COL_3,task1.getTime().toString());
        values.put(COL_4,task1.getPriority());
        return db.update(TABLE_NAME, values, UID + " = ?",
                new String[] { String.valueOf(task.getId())})!=(-1);
    }

    public Integer deleteData (Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {String.valueOf(task.getId())});
    }
    public Task getTask(long index,boolean isFirst,boolean isLast){
        Cursor cursor=getAllData();
        if (isFirst){
            cursor.moveToFirst();
            return new Task(cursor.getString(1),cursor.getString(3),cursor.getInt(4),cursor.getString(2));
        }
        if (isLast){
            cursor.moveToLast();
            return new Task(cursor.getString(1),cursor.getString(3),cursor.getInt(4),cursor.getString(2));
        }
        cursor.move((int)index);
        return new Task(cursor.getString(1),cursor.getString(3),cursor.getInt(4),cursor.getString(2));
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
