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

@Entity
@Table(name="pemeriksaan")
public class PemeriksaanModel implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;
	
	@NotNull
	@Column(name = "id_dokter", nullable = false)
	private Long idDokter;
	
	@NotNull
	@Column(name = "id_pasien", nullable = false)
	private Long idPasien;
	
	@NotNull
	@Column(name = "waktu", nullable = false)
	private Date waktu;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "deskripsi", nullable = false)
	private String deskripsi;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getIdDokter() {
		return idDokter;
	}

	public void setIdDokter(Long idDokter) {
		this.idDokter = idDokter;
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
