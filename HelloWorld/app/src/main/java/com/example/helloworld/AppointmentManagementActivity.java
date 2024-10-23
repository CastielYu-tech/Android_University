package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.List;
import java.util.ArrayList;

public class AppointmentManagementActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AppointmentAdapter adapter;
    private DBHelper dbHelper;
    private List<Appointment> appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_management);

        dbHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadAppointments();

        findViewById(R.id.btnAdd).setOnClickListener(v ->
                startActivity(new Intent(this, AddAppointmentActivity.class))
        );
    }

    private void loadAppointments() {
        appointments = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("appointments", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            Appointment appointment = new Appointment(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("department")),
                    cursor.getString(cursor.getColumnIndex("doctor_name")),
                    cursor.getString(cursor.getColumnIndex("time_slot")),
                    cursor.getFloat(cursor.getColumnIndex("price")),
                    cursor.getString(cursor.getColumnIndex("doctor_image")),
                    cursor.getInt(cursor.getColumnIndex("max_appointments")),
                    cursor.getString(cursor.getColumnIndex("available_days"))
            );
            appointments.add(appointment);
        }
        cursor.close();

        adapter = new AppointmentAdapter(appointments);
        recyclerView.setAdapter(adapter);
    }
}
