package com.apap.tugas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas.model.RequestPasienModel;

@Repository
public interface RequestPasienDb extends JpaRepository<RequestPasienModel, Long>{
	RequestPasienModel findByIdPasien(long idPasien);

}
