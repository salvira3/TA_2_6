package com.apap.tugas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas.model.KamarModel;

@Repository
public interface KamarDb extends JpaRepository<KamarModel, Long>{	

}
