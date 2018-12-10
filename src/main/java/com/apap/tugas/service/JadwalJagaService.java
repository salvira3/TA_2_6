package com.apap.tugas.service;

import com.apap.tugas.model.JadwalJagaModel;

import java.util.Optional;

public interface JadwalJagaService {
    JadwalJagaModel getJadwalJagaByIdDokter(long idDokter);
}
