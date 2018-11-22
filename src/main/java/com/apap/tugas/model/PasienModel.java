package com.apap.tugas.model;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PasienModel implements Serializable {
	private Long id;
	private String nama;
	private Date tanggalRujukan;
	private StatusPasienModel statusPasien;
	private String poliRujukan;
	public long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public Date getTanggalRujukan() {
		return tanggalRujukan;
	}
	public void setTanggalRujukan(Date tanggalRujukan) {
		this.tanggalRujukan = tanggalRujukan;
	}
	public StatusPasienModel getStatusPasien() {
		return statusPasien;
	}
	public void setStatusPasien(StatusPasienModel statusPasien) {
		this.statusPasien = statusPasien;
	}
	public String getPoliRujukan() {
		return poliRujukan;
	}
	public void setPoliRujukan(String poliRujukan) {
		this.poliRujukan = poliRujukan;
	}
	
    
   
}