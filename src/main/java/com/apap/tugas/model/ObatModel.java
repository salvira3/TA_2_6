package com.apap.tugas.model;

public class ObatModel {

    String namaMedicalSupplies;
    long idPasien;
    int jumlahMedicalSupplies;

    public String getNamaMedicalSupplies() {
        return namaMedicalSupplies;
    }

    public void setNamaMedicalSupplies(String namaMedicalSupplies) {
        this.namaMedicalSupplies = namaMedicalSupplies;
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
