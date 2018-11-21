package com.apap.tugas.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas.model.DokterModel;

@Repository
public interface DokterDb extends JpaRepository<DokterModel, Long> {
}