package com.apap.tugas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas.model.PemeriksaanModel;
@Repository
public interface PemeriksaanDb extends JpaRepository<PemeriksaanModel, Long> {

	


}
