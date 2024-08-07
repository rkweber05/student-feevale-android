package com.feevale.estudantessqlite;

import static android.provider.BaseColumns._ID;
import static com.feevale.estudantessqlite.StudentSQLiteDatabase.StudentTable.COLUMN_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class StudentSQLiteDatabase {

    Context ctx;
    public static final String DATABASE_NAME = "students.db";
    public static final Integer DATABASE_VERSION = 1;
    private final SQLiteDatabase db;

    public StudentSQLiteDatabase(Context ctx) {
        this.ctx = ctx;
        db = new StudentSQLiteDatabaseHelper().getWritableDatabase();
    }

    public Long addStudent(Student s) {
        ContentValues values = new ContentValues();
        values.put(StudentTable.COLUMN_NAME, s.getName());
        values.put(StudentTable.COLUMN_EMAIL, s.getEmail());
        values.put(StudentTable.COLUMN_COURSE, s.getCourse());

        return db.insert(StudentTable.TABLE_NAME, null, values);
    }

    public Student getStudent(Long id) {
        String cols[] = {
                _ID,
                StudentTable.COLUMN_NAME,
                StudentTable.COLUMN_EMAIL,
                StudentTable.COLUMN_COURSE
        };

        String args[] = {
                id.toString()
        };

        Cursor cursor = db.query(StudentTable.TABLE_NAME, cols, _ID+"=?", args, null, null, _ID);

        if (cursor.getCount() != 1) {
            return null;
        }

        cursor.moveToNext();
        Student s = new Student();
        s.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        s.setName(cursor.getString(cursor.getColumnIndex(StudentTable.COLUMN_NAME)));
        s.setCourse(cursor.getString(cursor.getColumnIndex(StudentTable.COLUMN_COURSE)));
        s.setEmail(cursor.getString(cursor.getColumnIndex(StudentTable.COLUMN_EMAIL)));

        return s;
    }

    public List<Student> getStudents() {
        String cols[] = {StudentTable._ID, StudentTable.COLUMN_NAME, StudentTable.COLUMN_EMAIL, StudentTable.COLUMN_COURSE};
        Cursor cursor = db.query(StudentTable.TABLE_NAME, cols, null, null, null, null, StudentTable.COLUMN_NAME);
        List<Student> students = new ArrayList<>();
        Student s;

        while (cursor.moveToNext()) {
            s = new Student();
            s.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
            s.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            s.setCourse(cursor.getString(cursor.getColumnIndex(StudentTable.COLUMN_COURSE)));
            s.setEmail(cursor.getString(cursor.getColumnIndex(StudentTable.COLUMN_EMAIL)));
            students.add(s);
        }
        return students;
    }

    public Integer removeStudent(Student s) {
        String args[] = {s.getId().toString()};
        return db.delete(StudentTable.TABLE_NAME, _ID + "=?", args);
    }

    public Integer updateStudent(Student s) {
        String args[] = {s.getId().toString()};
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, s.getName());
        values.put(StudentTable.COLUMN_EMAIL, s.getEmail());
        values.put(StudentTable.COLUMN_COURSE, s.getCourse());

        return db.update(StudentTable.TABLE_NAME, values, _ID + "=?", args);
    }

    public static class StudentTable implements BaseColumns {
        public static final String TABLE_NAME = "student";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_COURSE = "course";

        public static String getSQL() {
            String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID                  + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME          + " TEXT, " +
                    COLUMN_EMAIL         + " TEXT, " +
                    COLUMN_COURSE        + " TEXT) ";
            return sql;
        }
    }

    private class StudentSQLiteDatabaseHelper extends SQLiteOpenHelper {
        public StudentSQLiteDatabaseHelper(){
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(StudentTable.getSQL());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + StudentTable.TABLE_NAME);
            onCreate(db);
        }
    }
}
