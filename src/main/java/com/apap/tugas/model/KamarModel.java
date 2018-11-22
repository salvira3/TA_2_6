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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="kamar")
public class KamarModel implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Long id;
	
	@NotNull
	@Column(name = "id_pasien", nullable = true)
	private Long idPasien;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="paviliun_id", referencedColumnName="id", nullable= false)
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	@JsonIgnoreProperties(ignoreUnknown = true)
	private PaviliunModel paviliun;
	
	@NotNull
	@Column(name = "status_kamar", nullable = false)
	private Integer statusKamar;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPasien() {
		return idPasien;
	}

	public void setIdPasien(Long idPasien) {
		this.idPasien = idPasien;
	}

	public PaviliunModel getPaviliun() {
		return paviliun;
	}

	public void setPaviliun(PaviliunModel paviliun) {
		this.paviliun = paviliun;
	}

	public Integer getStatusKamar() {
		return statusKamar;
	}

	public void setStatusKamar(Integer statusKamar) {
		this.statusKamar = statusKamar;
	}
	
}
