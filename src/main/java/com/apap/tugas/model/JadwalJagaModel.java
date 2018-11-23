package com.apap.tugas.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="jadwal_jaga")
public class JadwalJagaModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dokter", referencedColumnName = "id",nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private DokterModel dokter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paviliun", referencedColumnName = "id",nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private PaviliunModel paviliun;

    @NotNull
    @Size(max = 255)
    @Column(name = "status_dokter")
    private String statusDokter;

    @NotNull
    @Size(max = 255)
    @Column(name = "daftar_hari_jaga")
    private String daftarHariJaga;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DokterModel getDokter() {
        return dokter;
    }

    public void setDokter(DokterModel dokter) {
        this.dokter = dokter;
    }

    public PaviliunModel getPaviliun() {
        return paviliun;
    }

    public void setPaviliun(PaviliunModel paviliun) {
        this.paviliun = paviliun;
    }

    public String getStatusDokter() {
        return statusDokter;
    }

    public void setStatusDokter(String statusDokter) {
        this.statusDokter = statusDokter;
    }

    public String getDaftarHariJaga() {
        return daftarHariJaga;
    }

    public void setDaftarHariJaga(String daftarHariJaga) {
        this.daftarHariJaga = daftarHariJaga;
    }
}
