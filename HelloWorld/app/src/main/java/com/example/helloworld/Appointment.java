package com.example.helloworld;

import java.util.Calendar;

public class Appointment {
    private int id;
    private String department;
    private String doctorName;
    private String doctorImage;
    private String timeSlot;
    private float price;
    private int maxAppointments;
    private String availableDays;

    // 构造函数
    public Appointment(int id, String department, String doctorName, String timeSlot, float price, String doctorImage, int maxAppointments, String availableDays) {
        this.id = id;
        this.department = department;
        this.doctorName = doctorName;
        this.timeSlot = timeSlot;
        this.price = price;
        this.doctorImage = doctorImage;
        this.maxAppointments = maxAppointments;
        this.availableDays = availableDays;
    }

    // Getter 方法
    public int getId() { return id; }
    public String getDepartment() { return department; }
    public String getDoctorName() { return doctorName; }
    public String getTimeSlot() { return timeSlot; }
    public float getPrice() { return price; }
    public String getDoctorImage() { return doctorImage; }
    public int getMaxAppointments() { return maxAppointments; }
    public String getAvailableDays() { return availableDays; }

    // Setter 方法
    public void setId(int id) { this.id = id; }
    public void setDepartment(String department) { this.department = department; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public void setPrice(float price) { this.price = price; }
    public void setDoctorImage(String doctorImage) { this.doctorImage = doctorImage; }
    public void setMaxAppointments(int maxAppointments) { this.maxAppointments = maxAppointments; }
    public void setAvailableDays(String availableDays) { this.availableDays = availableDays; }

    public boolean isAvailableToday() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return availableDays.contains(String.valueOf(dayOfWeek));
    }
}
