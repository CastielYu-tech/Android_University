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
import android.widget.ArrayAdapter;
import android.net.Uri;
import android.content.ContentValues;

public class AddAppointmentActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private DBHelper dbHelper;
    private String selectedImagePath;
    private Spinner spinnerDepartment;
    private EditText etDoctorName, etTimeSlot, etPrice, etMaxAppointments;
    private ImageView ivDoctorImage;
    private CheckBox[] dayCheckboxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        dbHelper = new DBHelper(this);
        initViews();
        setupDepartmentSpinner();

        findViewById(R.id.btnSelectImage).setOnClickListener(v -> selectImage());
        findViewById(R.id.btnSave).setOnClickListener(v -> saveAppointment());
    }

    private void initViews() {
        spinnerDepartment = findViewById(R.id.spinnerDepartment);
        etDoctorName = findViewById(R.id.etDoctorName);
        etTimeSlot = findViewById(R.id.etTimeSlot);
        etPrice = findViewById(R.id.etPrice);
        etMaxAppointments = findViewById(R.id.etMaxAppointments);
        ivDoctorImage = findViewById(R.id.ivDoctorImage);

        // 初始化星期几的CheckBox数组
        dayCheckboxes = new CheckBox[]{
                findViewById(R.id.cbSunday),
                findViewById(R.id.cbMonday),
                // 其他星期几的CheckBox
        };
    }

    private void setupDepartmentSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.departments, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapter);
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "选择图片"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            selectedImagePath = uri.toString();
            ivDoctorImage.setImageURI(uri);
        }
    }

    private void saveAppointment() {
        // 获取输入数据
        String department = spinnerDepartment.getSelectedItem().toString();
        String doctorName = etDoctorName.getText().toString();
        String timeSlot = etTimeSlot.getText().toString();
        float price = Float.parseFloat(etPrice.getText().toString());
        int maxAppointments = Integer.parseInt(etMaxAppointments.getText().toString());

        // 获取选中的星期
        StringBuilder availableDays = new StringBuilder();
        for (int i = 0; i < dayCheckboxes.length; i++) {
            if (dayCheckboxes[i].isChecked()) {
                availableDays.append(i + 1).append(",");
            }
        }

        // 保存到数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("department", department);
        values.put("doctor_name", doctorName);
        values.put("doctor_image", selectedImagePath);
        values.put("time_slot", timeSlot);
        values.put("price", price);
        values.put("max_appointments", maxAppointments);
        values.put("available_days", availableDays.toString());

        db.insert("appointments", null, values);
        finish();
    }
}