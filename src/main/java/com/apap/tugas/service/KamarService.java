package com.apap.tugas.service;
import java.util.List;
import java.util.Optional;

import com.apap.tugas.model.KamarModel;
import com.apap.tugas.model.PaviliunModel;

public interface KamarService {
	List<KamarModel> getAll();
	Optional<KamarModel> getKamarDetailById(long id);
	List<KamarModel> getKamarByPaviliun(PaviliunModel paviliun);
	KamarModel getKamarById(Long id);
}
