package com.example.helloworld;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ImageView;
import android.widget.CheckBox;
import android.os.Bundle;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "hospital.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建管理员表
        db.execSQL("CREATE TABLE admin (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "password TEXT NOT NULL)");

        // 创建预约数据表
        db.execSQL("CREATE TABLE appointments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "department TEXT NOT NULL," +
                "doctor_name TEXT NOT NULL," +
                "doctor_image TEXT," +
                "time_slot TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "max_appointments INTEGER NOT NULL," +
                "available_days TEXT NOT NULL)");

        // 插入默认管理员账号
        db.execSQL("INSERT INTO admin (username, password) VALUES ('admin', '123456')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS admin");
        db.execSQL("DROP TABLE IF EXISTS appointments");
        onCreate(db);
    }
}