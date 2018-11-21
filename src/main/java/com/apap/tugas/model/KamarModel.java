package com.apap.tugas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "kamar")
public class KamarModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "id_pasien", nullable = false)
	private Integer idPasien;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "status", nullable = false)
	private Short status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_paviliun", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private PaviliunModel paviliun;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIdPasien() {
		return idPasien;
	}

	public void setIdPasien(Integer idPasien) {
		this.idPasien = idPasien;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public PaviliunModel getPaviliun() {
		return paviliun;
	}

	public void setPaviliun(PaviliunModel paviliun) {
		this.paviliun = paviliun;
	}
	
}
