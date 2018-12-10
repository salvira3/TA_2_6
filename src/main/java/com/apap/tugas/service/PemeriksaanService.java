package com.apap.tugas.service;

import com.apap.tugas.model.PemeriksaanModel;

public interface PemeriksaanService {
	PemeriksaanModel getPemeriksaanDetailByIdPasien(long id);
	void updatePenanganan(long idPasien, PemeriksaanModel newPemeriksaan);	
	void add(PemeriksaanModel pemeriksaan);
}
