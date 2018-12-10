package com.apap.tugas.repository;

import com.apap.tugas.model.DokterModel;
import com.apap.tugas.model.JadwalJagaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JadwalJagaDb extends JpaRepository<JadwalJagaModel,Long> {
    Optional<JadwalJagaModel> findByDokter_Id(long idDokter);
}
