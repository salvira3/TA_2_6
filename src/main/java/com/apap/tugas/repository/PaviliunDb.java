package com.apap.tugas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas.model.PaviliunModel;

@Repository
public interface PaviliunDb extends JpaRepository<PaviliunModel, Long>{

}
