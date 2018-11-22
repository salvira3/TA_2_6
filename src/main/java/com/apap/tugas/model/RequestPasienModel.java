package com.apap.tugas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "request_pasien")
public class RequestPasienModel implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Column(name = "id_pasien", nullable = false)
	private long idPasien;
	
	@NotNull
	@Column(name = "assign_status", nullable = false)
	private int assignStatus;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdPasien() {
		return idPasien;
	}

	public void setIdPasien(long idPasien) {
		this.idPasien = idPasien;
	}

	public int getAssignStatus() {
		return assignStatus;
	}

	public void setAssignStatus(int assignStatus) {
		this.assignStatus = assignStatus;
	}

}
