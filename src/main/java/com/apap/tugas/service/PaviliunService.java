package com.apap.tugas.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas.model.PaviliunModel;


public interface PaviliunService {
	List<PaviliunModel> getAll();
	Optional<PaviliunModel> getPaviliunDetailById(long id);
}
