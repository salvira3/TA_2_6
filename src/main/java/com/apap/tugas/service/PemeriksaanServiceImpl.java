package com.apap.tugas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas.model.PemeriksaanModel;
import com.apap.tugas.repository.PemeriksaanDb;
@Service
@Transactional
public class PemeriksaanServiceImpl implements PemeriksaanService {
	@Autowired 
	PemeriksaanDb pemeriksaanDb;
	@Override
	public PemeriksaanModel getPemeriksaanDetailByIdPasien(long id) {
		// TODO Auto-generated method stub
		PemeriksaanModel tmp = new PemeriksaanModel();
		List<PemeriksaanModel> listPemeriksaan = pemeriksaanDb.findAll();
		for(PemeriksaanModel e : listPemeriksaan) {
			if(e.getIdPasien() == id) {
				tmp = e;
			}
		}
		return tmp;
	}
	@Override
	public void add(PemeriksaanModel pemeriksaan) {
		// TODO Auto-generated method stub
		pemeriksaanDb.save(pemeriksaan);
	}

}
