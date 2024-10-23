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
import android.database.Cursor;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        dbHelper = new DBHelper(this);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        findViewById(R.id.btnLogin).setOnClickListener(v -> login());
    }

    private void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("admin",
                null,
                "username = ? AND password = ?",
                new String[]{username, password},
                null, null, null);

        if (cursor.moveToFirst()) {
            startActivity(new Intent(this, AppointmentManagementActivity.class));
            finish();
        } else {
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
}