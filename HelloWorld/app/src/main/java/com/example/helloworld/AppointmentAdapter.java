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
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.net.Uri;


public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private List<Appointment> appointments;
    private Context context;

    public AppointmentAdapter(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.tvDepartment.setText("科室：" + appointment.getDepartment());
        holder.tvDoctorName.setText("医生：" + appointment.getDoctorName());
        holder.tvTimeSlot.setText("时间段：" + appointment.getTimeSlot());
        holder.tvPrice.setText("价格：¥" + appointment.getPrice());

        if (appointment.getDoctorImage() != null) {
            holder.ivDoctor.setImageURI(Uri.parse(appointment.getDoctorImage()));
        }

        holder.btnEdit.setOnClickListener(v -> {
            // 处理编辑操作
            Intent intent = new Intent(context, AddAppointmentActivity.class);
            intent.putExtra("appointment_id", appointment.getId());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            // 处理删除操作
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete("appointments", "id = ?",
                    new String[]{String.valueOf(appointment.getId())});
            appointments.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDepartment, tvDoctorName, tvTimeSlot, tvPrice;
        ImageView ivDoctor;
        Button btnEdit, btnDelete;

        ViewHolder(View view) {
            super(view);
            tvDepartment = view.findViewById(R.id.tvDepartment);
            tvDoctorName = view.findViewById(R.id.tvDoctorName);
            tvTimeSlot = view.findViewById(R.id.tvTimeSlot);
            tvPrice = view.findViewById(R.id.tvPrice);
            ivDoctor = view.findViewById(R.id.ivDoctor);
            btnEdit = view.findViewById(R.id.btnEdit);
            btnDelete = view.findViewById(R.id.btnDelete);
        }
    }
}