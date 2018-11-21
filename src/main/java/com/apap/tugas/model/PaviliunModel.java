package com.apap.tugas.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="paviliun")
public class PaviliunModel implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama_paviliun", nullable = false)
	private String namaPaviliun;

	@NotNull
	@Size(max = 255)
	@Column(name = "tipe_pasien", nullable = false)
	private String tipePasien;
	
	@NotNull
	@Column(name = "status_paviliun", nullable = false)
	private int statusPaviliun;
	
	@OneToMany(mappedBy = "paviliun", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JsonIgnore
	private List<KamarModel> listKamar;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNamaPaviliun() {
		return namaPaviliun;
	}

	public void setNamaPaviliun(String namaPaviliun) {
		this.namaPaviliun = namaPaviliun;
	}

	public String getTipePasien() {
		return tipePasien;
	}

	public void setTipePasien(String tipePasien) {
		this.tipePasien = tipePasien;
	}

	public int getStatusPaviliun() {
		return statusPaviliun;
	}

	public void setStatusPaviliun(int statusPaviliun) {
		this.statusPaviliun = statusPaviliun;
	}

	public List<KamarModel> getListKamar() {
		return listKamar;
	}

	public void setListKamar(List<KamarModel> listKamar) {
		this.listKamar = listKamar;
	}
	
}
