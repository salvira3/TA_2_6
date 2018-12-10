package com.apap.tugas.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class PemeriksaanDataModel implements Serializable{
	private DokterModel dokter;
	private Long idPasien;
	private Long idPemeriksaan;
	private String deskripsi;
	private Date waktu;

	public DokterModel getDokter() {
		return dokter;
	}

	public Long getIdPemeriksaan() {
		return idPemeriksaan;
	}

	public void setIdPemeriksaan(Long idPemeriksaan) {
		this.idPemeriksaan = idPemeriksaan;
	}

	public void setDokter(DokterModel dokter) {
		this.dokter = dokter;
	}

	public Long getIdPasien() {
		return idPasien;
	}

	public void setIdPasien(Long idPasien) {
		this.idPasien = idPasien;
	}

	public Date getWaktu() {
		return waktu;
	}

	public void setWaktu(Date waktu) {
		this.waktu = waktu;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}
	
	
}
