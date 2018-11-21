package com.apap.tugas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas.model.KamarModel;
import com.apap.tugas.repository.KamarDb;

@Service
@Transactional
public class KamarServiceImpl implements KamarService{
	@Autowired 
	private KamarDb kamarDb;

	@Override
	public List<KamarModel> getAll() {
		// TODO Auto-generated method stub
		return kamarDb.findAll();
	}
	
}
