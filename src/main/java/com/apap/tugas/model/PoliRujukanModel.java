package com.apap.tugas.model;

import java.io.Serializable;

public class PoliRujukanModel implements Serializable{
	private int id;
	private String nama;

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
