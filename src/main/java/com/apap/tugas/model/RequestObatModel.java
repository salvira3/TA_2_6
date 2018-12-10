package com.apap.tugas.model;

import java.util.ArrayList;

public class RequestObatModel {

    long idPasien;
    int jumlahMedicalSupplies;
    ArrayList<MedicalSuppliesModel> listPermintaanMedicalSupplies;

    public ArrayList<MedicalSuppliesModel> getListPermintaanMedicalSupplies() {
        return listPermintaanMedicalSupplies;
    }

    public void setListPermintaanMedicalSupplies(ArrayList<MedicalSuppliesModel> listPermintaanMedicalSupplies) {
        this.listPermintaanMedicalSupplies = listPermintaanMedicalSupplies;
    }

    public long getIdPasien() {
        return idPasien;
    }

    public void setIdPasien(long idPasien) {
        this.idPasien = idPasien;
    }

    public int getJumlahMedicalSupplies() {
        return jumlahMedicalSupplies;
    }

    public void setJumlahMedicalSupplies(int jumlahMedicalSupplies) {
        this.jumlahMedicalSupplies = jumlahMedicalSupplies;
    }
}
