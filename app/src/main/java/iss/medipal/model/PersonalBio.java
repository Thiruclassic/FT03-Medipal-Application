package iss.medipal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Thirumal on 2/20/2017.
 */
public class PersonalBio {

    int id;
    String name;
    String dob;
    String idNo;
    String address;
    String postalCode;
    int height;
    String bloodType;
    ArrayList<Appointment> appointments;
    List<HealthBio> healthBios;
    ArrayList<Medicine> medicines;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<HealthBio> getHealthBios() {
        return healthBios;
    }

    public void setHealthBios(List<HealthBio> healthBios) {
        this.healthBios = healthBios;
    }

    public ArrayList<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(ArrayList<Medicine> medicines) {
        this.medicines = medicines;
    }
}
