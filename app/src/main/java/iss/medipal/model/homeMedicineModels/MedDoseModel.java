package iss.medipal.model.homeMedicineModels;

import java.io.Serializable;

/**
 * Created by junaidramis on 19/3/17.
 */

public class MedDoseModel implements Serializable {

    public int idMed;
    public int status;
    public String date;
    public String doseTime;
    public String drugName;

    public int getId_med() {
        return idMed;
    }

    public void setIdMed(int idMed) {
        this.idMed = idMed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public int getIdMed() {
        return idMed;
    }

    public String getDoseTime() {
        return doseTime;
    }

    public void setDoseTime(String doseTime) {
        this.doseTime = doseTime;
    }
}
