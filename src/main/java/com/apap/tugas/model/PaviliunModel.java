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

@Entity
@Table(name = "paviliun")
public class PaviliunModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama_paviliun", nullable = false)
	private String namaPaviliun;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "tipe_pasien", nullable = false)
	private String tipePasien;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "status", nullable = false)
	private Short status;
	
	@OneToMany(mappedBy = "kamar", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<KamarModel> listKamar;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public List<KamarModel> getListKamar() {
		return listKamar;
	}

	public void setListKamar(List<KamarModel> listKamar) {
		this.listKamar = listKamar;
	}
	
}
