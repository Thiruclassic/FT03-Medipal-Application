package iss.medipal.dao;

import java.util.List;

import iss.medipal.model.Medicine;


/**
 * Created by Naveen on 2/24/17.
 */

public interface MedicineDao {
    public int addMedicine(Medicine medicine);
    public int updateMedicine(Medicine medicine);
    public int deleteMedicine(int medicineId);

    public Medicine getMedicinebyId(int medicineId);
    public Medicine getMedicinebyName(int medicineId);
    public List<Medicine> getAllMedicines();
    public List<String> getAllMedicinesName();


}
